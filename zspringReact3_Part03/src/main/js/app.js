'use strict';

//...webpack 을 이용하여 조합을 하므로, 필요한 모듈을 불러옴.
const React = require('react');
const ReactDOM = require('react-dom');

const when = require('when'); //...added since Part03.

//...client is custom code that configures rest.js to include support for 
//   HAL, URI Templates, and other things. 
//   It also sets the default Accept request header to application/hal+json. 
//   You can read the code here.
const client = require('./client');

//function to hop multiple links by "rel"
const follow = require('./follow'); 

//...유일하게 하드코딩할 경로임.
const root = '/api';
//...E.require___________________________________________________________________


//...컴포넌트를 정의하는 것에 기초하는 것이 React 임.
//   하나의 컴포넌트가 여러개의 인스턴스를 담는 부모-자식 관계로 여러 층으로 
//   확장하기에 쉬운 개념임.
//   먼저, 모든 컴포넌트들에 대한 최상위 컨테이너를 갖는 것이 용이함.
//...React.createClass({…​}) : React 컴포넌트를 생성하는 메서드임.
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: [] 
//...S.PagingAndSortingRepository 사용으로 추가함.		
						,attributes: [] 
						,pageSize: 2 
						,links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);//...added since Part03.
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
//...E.PagingAndSortingRepository 사용으로 추가함.	
	}//...E.constructor(props).	

	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}//...E.componentDidMount().	
	
	loadFromServer(pageSize) {
/*...follow() 함수는 employees 컬렉션 자원에 접근함.
//...follow.js 함수 사용으로 root 에서 바로 시작해서 필요한 곳으로 네비게이트 가능함.
//...client : REST 호출을 하는데 이용되는 객체임.
//...Data 는 rest.js 인자인 client 를 통해서 로딩됨.		
//   root : 시작하는 root URI.
//   an array of relationships to navigate along. 
//		 Each one can be a string or an object.
//   The array of relationships can be as simple as ["employees"], 
//   meaning when the first call is made, look in _links for the relationship (or rel) 
//   named employees. 
//   Find its href and navigate to it. 
//   If there is another relationship in the array, rinse and repeat.*/		
		follow(client, root, [
/*...'?size=<pageSize>' 는 rel 에 끼울 수 있는 쿼리 변수임.	*/
			{rel: 'employees', params: {size: pageSize}}]
/*...(employeeCollection =>) 구문은 JSON 스키마 데이터에 대한 호출을 생성시킴.
//   이 구문의 부수적인 'then' 구문은 메타데이터와 <App/> 컴포넌트에 있는
//   네비게이셔널한 링크를 저장함.
//   내부의 promise 는 employeeCollection 을 반환함.
//   당신이 메타데이터를 갖는 동안에 그렇게 컬렉션은 다음 호출로 건네질 수 있음.
//...이러한 체인은 다른 곳에서도 구현될 수 있음.
//   예를 들어, 다른 페이지로 점핑하는데 사용되는 onNavigate() 도 개별 자원들을
//   가져오도록 변경되었음.
//   아래와 같은 구성이 대부분 동일함.*/
		).then(employeeCollection => {
			return client({
				method: 'GET',
				path: employeeCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				this.links = employeeCollection.entity._links;
				return employeeCollection;
			});
/*...employees 컬렉션을 GET promises 의 배열 형태로 변환해서 각 개별 자원을 가져오게 함.
//   각 employee 에 대한 ETag 헤더가 필요한 것이 이것임.*/
		}).then(employeeCollection => {
/*...Part02 에서는 컬렉션 자원을 이용해서 데이터를 모아서 UI 의 HTML 테이블에 상주시켰음.
//   Spring Data REST 를 가지고, _embedded 데이터 조합은 데이터에 대한 미리보기로 간주됨.
//   ETags 와 같은 헤더를 구하는 유용한 데이터를 한눈에 볼 수 있는 반면, 각 자원을 개별적
//   으로 가져와야 할 필요가 있음.*/
			return employeeCollection.entity._embedded.employees.map(employee =>
					client({
						method: 'GET',
						path: employee._links.self.href
					})
			);
/*...GET promises 배열을 가지고 when.all() 을 가지는 단일 promise 로 병합해서
//   모든 GET promises 가 resolved 될 때 resolved 됨. */
		}).then(employeePromises => {
			return when.all(employeePromises);
/*...데이터를 섞는 방법을 이용하여 UI 상태가 변경되는 곳인 done(employees =>) 으로 감쌈.*/ 
		}).done(employees => {
			this.setState({
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}//...E.loadFromServer(pageSize).	


	onCreate(newEmployee) {
		var self = this;
//...follow() 함수는 POST 작업이 이뤄지는 employees 자원에 대해 네비게이트하는 함수이고,
//   이 경우, 어떠한 변수를 적용할 필요가 없었고, 그래서 스트링 기반의 배열인 rels 이면 충분함.
//   이 상황에서, POST 호출이 반환됨.		
		follow(client, root, ['employees']).then(response => {
			return client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newEmployee,
				headers: {'Content-Type': 'application/json'}
			})
//   이것은 다음 then() 절로 하여금 POST 의 결과를 진행하는 것을 핸들하게끔 함.
		}).then(response => {
			return follow(client, root, [{rel: 'employees', params: {'size': self.state.pageSize}}]);
//...새로운 데이터는 새로운 페이지에서 보여지는게 일반적이므로 동일한 페이지 크기가 적용된
//   새로운 데이터 batch 를 가져오기 위해 done() 을 반환함.
//   사용자가 새롭게 추가된 employee 를 보고자 원할 수 있어서, 하이퍼미디어 컨트롤을 
//   이용해서 마지막(last)항목으로 네비게이트할 수 있음.
		}).done(response => {
			self.onNavigate(response.entity._links.last.href);
		});
	}//...E.onCreate(newEmployee).		


	onUpdate(employee, updatedEmployee) {
		client({
			method: 'PUT',
			path: employee.entity._links.self.href,
			entity: updatedEmployee,
			headers: {
				'Content-Type': 'application/json',
				'If-Match': employee.headers.Etag
			}
		}).done(response => {
			this.loadFromServer(this.state.pageSize);
		}, response => {
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' +
					employee.entity._links.self.href + '. Your copy is stale.');
			}
		});
	}//...E.onUpdate(employee, updatedEmployee).added since Part03.

//...페이지 기반의 UI 를 가진 레코드를 삭제한 후 적용되는 행위는 약간 까다로운데,
//  동일한 페이지 크기를 적용하여 서버로부터 전체 데이터를 다시 로드한 후, 첫번째
//  페이지를 보여줌.
	onDelete(employee) {
		client({method: 'DELETE', path: employee.entity._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}//...E.onDelete(employee).

//...백엔드에서 페이지를 설정한 PagingAndSortingRepository 를 상속해서 사용했음.
//  onCreate(newEmployee) 에서 새로운 종업원을 생성하면서 last 페이지로 점핑하는 
//  page control 을 사용했음.
//  이용가능한 네비게이션 링크를 근거로 동적으로 컨트롤을 조정하여 페이지 이동을 할 수 있어서 편리함.	
	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(employeeCollection => {
			this.links = employeeCollection.entity._links;

			return employeeCollection.entity._embedded.employees.map(employee =>
					client({
						method: 'GET',
						path: employee._links.self.href
					})
			);
		}).then(employeePromises => {
			return when.all(employeePromises);
		}).done(employees => {
			this.setState({
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}//...E.onNavigate(navUri).

//...새로운 페이지 크기는 모든 navigation links 에 대해 변화를 일으킴.
//  데이터를 새로 가져오고 처음부터 시작하는 것이 가장 좋음.	
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}//...E.updatePageSize(pageSize).

	
//...render : 화면에 컴포넌트를 그리는 API 임.
//...state 가 변경되면, render() 이 호출됨.	
	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
				<EmployeeList employees={this.state.employees}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  attributes={this.state.attributes} /*...added since Part03.*/
							  onNavigate={this.onNavigate}
							  onUpdate={this.onUpdate} /*...added since Part03.*/
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>
		)
	}
}
//...E.App___________________________________________________________________


//...$ curl http://localhost:8080/api/profile/employees -H 'Accept:application/schema+json'
//   의 실행결과에 나온 메타데이터를 가지고, UI 에 대한 다른 컨트롤을 추가할 수 있음.
//...handleSubmit(), render() 함수를 가짐.
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}//...E.constructor(props).

	handleSubmit(e) {
//...이벤트가 계층으로 부푸는것을 중지시킴.		
		e.preventDefault();
		var newEmployee = {};
//...동일한 JSON 스키마 attribute 속성을 이용하여 각 <input> 을 찾음.
//...ReactDOM.findDOMNode(this.refs[attribute]) 를 이용하여 각각의 <input> 을 찾음.
		this.props.attributes.forEach(attribute => {
//...this.refs : name 을 이용하여 특정한 React 컴포넌트를 잡는 방법임.
//   단지 가상 DOM 컴포넌트를 얻음.
//   실제 DOM 엘리먼트를 얻기 위해서는 React.findDOMNode() 를 이용함.
			newEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		
//...onCreate() 콜백을 호출함.
//   이 함수는 최상위 App.onCreate 내부에 있고 이 React 컴포넌트에 다른 속성으로 제공됐음.
		this.props.onCreate(newEmployee);
		
		// clear out the dialog's inputs		
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = ''; 
		});
		
		// Navigate away from the dialog to hide it.
		window.location = "#";
	}//...E.handleSubmit(e).

/*...attributes 속성에서 발견된 JSON 스키마 데이터에 대한 맵핑을 해서 이를 
//	<p><input></p> 엘리먼트 배열형태로 변환함.*/
	render() {
		var inputs = this.props.attributes.map(attribute =>
//...key : React 가 복수의 자식 노드를 구분하는데 필요한 단순한 텍스트 기반의 항목 필드임.
//   ref : React 는 name 이 아닌 ref 로 특정한 DOM 노드를 가지는 메커니즘임.
		    <p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field" />
			</p>
		);
		
//...최상위의 div 는 대화상자를 여는 버튼에 관한 앵커 태그임.
//   내장된 div 는 감춰진 대화상자임.
//			 이 예제에서는 자바스크립트가 아닌 순수한 HTML5 와 CSS3 를 사용함.
		return (
			<div>
				<a href="#createEmployee">Create</a>

				<div id="createEmployee" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new employee</h2>

						<form>
							{inputs}
{/*...버튼은 onClick={this.handleSubmit} 의 이벤트 핸들러를 가짐.*/}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
};
//...E.CreateDialog______________________________________________________________

/*
 * ...CreateDialog 콤포넌트와 유사하게 handleSubmit() 함수와 render() 함수를 가짐.
 */
class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		var updatedEmployee = {};
		this.props.attributes.forEach(attribute => {
			updatedEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onUpdate(this.props.employee, updatedEmployee);
		window.location = "#";
	}

	render() {
		var inputs = this.props.attributes.map(attribute =>
				<p key={this.props.employee.entity[attribute]}>
					<input type="text" placeholder={attribute}
						   defaultValue={this.props.employee.entity[attribute]}
						   ref={attribute} className="field" />
				</p>
		);

		var dialogId = "updateEmployee-" + this.props.employee.entity._links.self.href;

		return (
			<div key={this.props.employee.entity._links.self.href}>
				<a href={"#" + dialogId}>Update</a>
				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Update an employee</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Update</button>
						</form>
					</div>
				</div>
			</div>
		)
	}

};
//...E.UpdateDialogadded since Part03.___________________________________________



class EmployeeList extends React.Component {

	constructor(props) {
		super(props);
		this.handleNavFirst = this.handleNavFirst.bind(this);
		this.handleNavPrev = this.handleNavPrev.bind(this);
		this.handleNavNext = this.handleNavNext.bind(this);
		this.handleNavLast = this.handleNavLast.bind(this);
		this.handleInput = this.handleInput.bind(this);
	}//...E.constructor(props).

	//...onNavigate 를 속성으로 받아서 원하는 페이지로 네비게이트할 수 있음.
	handleNavFirst(e){
		e.preventDefault();
		this.props.onNavigate(this.props.links.first.href);
	}
	handleNavPrev(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.prev.href);
	}
	handleNavNext(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.next.href);
	}
	handleNavLast(e) {
		e.preventDefault();
		this.props.onNavigate(this.props.links.last.href);
	}//...E.handleNavigation.
	
	handleInput(e) {
		e.preventDefault();
//...React 의 findDOMNode() 헬퍼함수를 통해서 <input> 태그의 ref 속성을 이용하여
//   DOM 노드와 그것의 정확한 value 를 알아냄.
		var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
//...스트링의 숫자인지 확인하여서 입력값이 정말 숫자인지 확인함.
//   만약 숫자라면, App 리액트 콤포넌트에 새로운 페이지 크기를 보내는 콜백을 촉발함.
//   만약 숫자가 아니라면, 입력한 글자는 그냥 제거됨.
		if (/^[0-9]+$/.test(pageSize)) {
			this.props.updatePageSize(pageSize);
		} else {
			ReactDOM.findDOMNode(this.refs.pageSize).value = pageSize.substring(0, pageSize.length - 1);
		}
	}//...E.handleInput(e).	
	
	render() {
		var employees = this.props.employees.map(employee =>
/*...employee._links.self.href 와 employee 로 부터 값을 받음.	
//   (http://localhost:8080/api/employees/1)
//...★Whenever you work with Spring Data REST, 
//		 the self link IS the key for a given resource. 
//   React needs a unique identifer for child nodes, 
//		 and _links.self.href is perfect.*/		
				<Employee key={employee.entity._links.self.href}
						  employee={employee}
						  attributes={this.props.attributes}
						  onUpdate={this.props.onUpdate}
						  onDelete={this.props.onDelete}/>
		);

/*...it still transforms this.props.employees into an array of <Element /> components. 
//   Then it builds up an array of navLinks, an array of HTML buttons.
//...HAL 실행결과 나타난 link 에 해당하는 주소 속성값 유무에 따라 해당 버튼을 만드는 것 같음.*/			
		var navLinks = [];
		if ("first" in this.props.links) {
			navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
		}
		if ("prev" in this.props.links) {
			navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
		}
		if ("next" in this.props.links) {
			navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
		}
		if ("last" in this.props.links) {
			navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
		}

		return (
/*...하이퍼미디어가 페이지 크기를 변경하는 것이 얼마나 반짝이냐는 것을 볼 수 있는 한 방법임.
//   Spring Data REST 는 부드럽게 페이지 크기에 기반한 navigational links 를 변경함.
//   ref="pageSize" : this.refs.pageSize 를 통해 엘리먼트를 가질 수 있음.
//   defaultValue : 상태의 페이지크기를 초기화함.
//   onInput : 이벤트 핸들러를 등록함.*/
			<div>
				<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Description</th>
							<th></th>
							<th></th>
						</tr>
						{employees}
					</tbody>
				</table>
				<div>
{/*...React 는 XML 기반이므로, 왼쪽화살표태그(<)를 button태그 엘리먼트 안에 넣을 수 없으므로, 
 인코드된 버전으로 사용해야함.*/}		
					{navLinks}
				</div>
			</div>
		)
	}
	// end::employee-list-render[]
}
//...E.EmployeeList__________________________________________________________


class Employee extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}//...E.constructor(props).	

	handleDelete() {
		this.props.onDelete(this.props.employee);
	}//...E.handleDelete().	

	render() {
		return (
			<tr>
				<td>{this.props.employee.entity.firstName}</td>
				<td>{this.props.employee.entity.lastName}</td>
				<td>{this.props.employee.entity.description}</td>
				<td>
					<UpdateDialog employee={this.props.employee}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate}/>
				</td>
				<td>
{/*...Employee 컴포넌트는 행의 마지막 항목에 삭제버튼을 보여줌.
//...한 곳에서 가장 최상위 컴포넌트에서 state 를 관리하는 것이 가장 쉽다는 것을 보여줌.
//   컴포넌트에 특화된 세부사항인(this.props.onDelete(this.props.employee)) 를 가진
//   콜백을 촉발함으로써, 컴포넌트간의 행동을 지휘하기 아주 쉬움.*/}
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}//...E.render().
}
//...E.Employee______________________________________________________________

ReactDOM.render(
	<App />,
	document.getElementById('react')
)
