'use strict';
//...Part 4 : Event.
//...webpack 을 이용하여 조합을 하므로, 필요한 모듈을 불러옴.
const React = require('react');
const ReactDOM = require('react-dom');

const when = require('when');//...added since Part03.

//...client is custom code that configures rest.js to include support for 
//   HAL, URI Templates, and other things. 
//   It also sets the default Accept request header to application/hal+json. 
//   You can read the code here.
const client = require('./client');

//function to hop multiple links by "rel"
const follow = require('./follow');

const stompClient = require('./websocket-listener');//...added since Part04.

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
						, page: 1 //...added since Part04.
						,pageSize: 2 
						,links: {}};
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onUpdate = this.onUpdate.bind(this);//...added since Part03.
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
		this.refreshCurrentPage = this.refreshCurrentPage.bind(this);//...added since Part04.
		this.refreshAndGoToLastPage = this.refreshAndGoToLastPage.bind(this);//...added since Part04.
//...E.PagingAndSortingRepository 사용으로 추가함.	
	}//...E.constructor(props).	


/*...before :
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}	
...after : 
   Registering for WebSocket events
   In React, a component’s componentDidMount() is the function that gets called 
   after it has been rendered in the DOM. 
   That is also the right time to register for WebSocket events, because the component 
   is now online and ready for business. 
*/
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
//...added since Part04.		
//...The second line shows an array of JavaScript objects being registered for WebSocket 
//   events, each with a route and a callback.
//...When a new employee is created, the behavior is to refresh the data set and then use 
//   the paging links to navigate to the last page. 
//...it’s better to use existing links and only go down that road if there is a performance
//   -driving reason to do so.	
//...When an employee is updated or deleted, the behavior is to refresh the current page. 
//   When you update a record, it impacts the page your are viewing. 
//   When you delete a record on the current page, a record from the next page will get 
//   pulled into the current one, hence the need to also refresh the current page.	
//...실질적인 처리 작업은 refreshAndGoToLastPage(message), refreshCurrentPage(message) 임.		
		stompClient.register([
			{route: '/topic/newEmployee', callback: this.refreshAndGoToLastPage},
			{route: '/topic/updateEmployee', callback: this.refreshCurrentPage},
			{route: '/topic/deleteEmployee', callback: this.refreshCurrentPage}
		]);
	}//...E.componentDidMount().
	
/*...before :
    loadFromServer() 를 collection 을 가져오고 나서 URI를 사용하여 각 개별 자원을 조회하도록 변경함.
    아래와 같은 체인은 다른 페이지로 점핑해서 개별 자원들을 가져오는데 사용되는 onNavigate() 와 같은
    다른 곳에서도 구현됨.
    1. follow() 함수는 employees 컬렉션 자원에 접근함.
			root 에서 바로 시작해서 필요한 곳으로 네비게이트 가능함.
			client : REST 호출을 하는데 이용되는 객체임.
				Data 는 rest.js 인자인 client 를 통해서 로딩됨.
			root : 시작하는 root URI.
				an array of relationships to navigate along.
				Each one can be a string or an object.
				The array of relationships can be as simple as ["employees"], 
				meaning when the first call is made, look in _links for the relationship (or rel) 
				named employees. 
				Find its href and navigate to it. 
				If there is another relationship in the array, rinse and repeat.
				
	2. then(employeeCollection => ...) 구문은 JSON 스키마 데이터에 대한 호출을 생성시킴.
	   이 구문의 부수적인 'then' 구문은 메타데이터와 <App/> 컴포넌트에 있는 네비게이셔널한 
	   링크를 저장함.
	   내부의 promise 는 employeeCollection 을 반환함.
	   당신이 메타데이터를 갖는 동안에 그렇게 컬렉션은 다음 호출로 건네질 수 있음.
	   
	3. then(employeeCollection =>...) 구문은 employees 컬렉션을 GET promises 의 배열 형태로 
	   변환해서 각 개별 자원을 가져오게 함.
	   각 employee 에 대한 ETag 헤더가 필요한 것이 여기임.

	4. then(employeePromises =>) 구문은 GET promises 배열을 가지고 when.all() 을 가지는 
	   단일 promise 로 병합해서 모든 GET promises 가 resolved 될 때 resolved 됨.
	   
	5. done(employees =>...) 구문은 데이터를 섞는 방법을 이용하여 UI 상태가 변경되는 곳인
	   done(employees =>) 으로 마감함.
	   
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'employees', params: {size: pageSize}}]
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
		}).then(employeeCollection => {
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
				page: this.page, //...added since Part04.
				
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}//...E.loadFromServer(pageSize).
 ...after : 
 	this.page = employeeCollection.entity.page; //...added since Part04. 
*/	
	loadFromServer(pageSize) {
		follow(client, root, [
			{rel: 'employees', params: {size: pageSize}}]
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
		}).then(employeeCollection => {
			this.page = employeeCollection.entity.page; //...added since Part04.
			
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
				page: this.page, //...added since Part04.
				
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: this.links
			});
		});
	}//...E.loadFromServer(pageSize).
	

//...새로운 페이지 크기는 모든 navigation links 에 대해 변화를 일으킴.
//  데이터를 새로 가져오고 처음부터 시작하는 것이 가장 좋음.
	updatePageSize(pageSize) {
		if (pageSize !== this.state.pageSize) {
			this.loadFromServer(pageSize);
		}
	}//...E.updatePageSize(pageSize).	


/*...before :
	onCreate(newEmployee) {
		var self = this;
		follow(client, root, ['employees']).then(response => {
			return client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newEmployee,
				headers: {'Content-Type': 'application/json'}
			})
		}).then(response => {
			return follow(client, root, [{rel: 'employees', params: {'size': self.state.pageSize}}]);
		}).done(response => {
			self.onNavigate(response.entity._links.last.href);
		});
	}//...E.onCreate(newEmployee).
 ...after : 
//...WebSocket 메시지가 도착할 때, UI 의 상태를 업데이트하는 방법들을
//   (refreshAndGoToLastPage(message), refreshCurrentPage(message)) 추가했으므로
//   상태를 업데이트하는 소스의 중복을 제거해서 단순화함.
//   In other words, submit your POST, PUT, and DELETE calls, 
//   but don’t use their results to update the UI’s state. 
//   Instead, wait for the WebSocket event to circle back and then do the update.
//   ★POST, PUT, DELETE 호출을 제출하고, UI 의 상태를 업데이트하는 그것들의 결과를 이용하지 않음.
//   → onCreate(newEmployee), onUpdate(employee, updatedEmployee), onDelete(employee) 가 적용대상임.
//   대신에, WebSocket 이벤트가 되돌아와서 업데이트를 수행하도록 기다림.
 */
	onCreate(newEmployee) {
//...Here, the follow() function is used to get to the employees link, 
//   and then the POST operation is applied.
//   follow() 함수는 employees 링크에 도달하도록 사용된 후, POST 작업이 적용됨.
//...Notice how client({method: 'GET' …​}) has no then() or done() like before? 
//   The event handler to listen for updates is now found in refreshAndGoToLastPage().
//   ★이전과 달리 어째서 client().then().done() 으로 구성되지 않을 수 있는 이유는
//   업데이트를 듣는 이벤트 핸들러가 refreshAndGoToLastPage() 에 있기 때문임.
		follow(client, root, ['employees']).done(response => {
			client({
				method: 'POST',
				path: response.entity._links.self.href,
				entity: newEmployee,
				headers: {'Content-Type': 'application/json'}
			})
		})
	}//...E.onCreate(newEmployee).
	
	
/*
 * ...'IF-Match' 요청 헤더를 가진 PUT 은 Spring Data REST 로 하여금 값이
 *    현재 버전과 대조하도록 확인하게 함.
 *    Spring Data REST 는 ETag 응답 헤더로서의 값을 도움.
 *    만약 일치하지 않으면, Spring Data REST 는 'HTTP 412 Precondition Failed' 로
 *    실패하게 됨.
 */
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
			/* Let the websocket handler update the state */
			//this.loadFromServer(this.state.pageSize);//XXX.commented since Part4.XXX.
		}, response => {
			if (response.status.code === 412) {
				alert('DENIED: Unable to update ' +
					employee.entity._links.self.href + '. Your copy is stale.');
			}
		});
	}//...E.onUpdate(employee, updatedEmployee).added since Part03.	
	
	

/*...before :
//...페이지 기반의 UI 를 가진 레코드를 삭제한 후 적용되는 행위는 약간 까다로운데,
//  동일한 페이지 크기를 적용하여 서버로부터 전체 데이터를 다시 로드한 후, 첫번째
//  페이지를 보여줌.
	onDelete(employee) {
		client({method: 'DELETE', path: employee.entity._links.self.href}).done(response => {
			this.loadFromServer(this.state.pageSize);
		});
	}//...E.onDelete(employee).
 ...after : */
	onDelete(employee) {
		client({method: 'DELETE', path: employee.entity._links.self.href});
	}//...E.onDelete(employee).
		
	
/*...before :
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
 ...after : */
	onNavigate(navUri) {
		client({
			method: 'GET',
			path: navUri
		}).then(employeeCollection => {
			this.links = employeeCollection.entity._links;
			
			this.page = employeeCollection.entity.page; //...added since Part04.

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
				page: this.page, //...added since Part04.
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}//...E.onNavigate(navUri).

/*
 * ...Reacting to WebSocket events and updating the UI state.
 *    refreshAndGoToLastPage(message), refreshCurrentPage(message) : 
 *    WebSocket 이벤트가 수신될 때, UI 상태를 업데이트하는데 사용되는 2개의 콜백을 가진 함수임.
 */
	refreshAndGoToLastPage(message) {
		follow(client, root, [{
			rel: 'employees',
			params: {size: this.state.pageSize}
		}]).done(response => {
			if (response.entity._links.last !== undefined) {
				this.onNavigate(response.entity._links.last.href);
			} else {
				this.onNavigate(response.entity._links.self.href);
			}
		})
	}//...E.refreshAndGoToLastPage(message).websocket-handlers[].added since Part04.

	refreshCurrentPage(message) {
		follow(client, root, [{
			rel: 'employees',
//...This fetches the same page you are currently looking at and updates the state accordingly.			
			params: {
				size: this.state.pageSize,
				page: this.state.page.number
			}
		}]).then(employeeCollection => {
			this.links = employeeCollection.entity._links;
			this.page = employeeCollection.entity.page;

			return employeeCollection.entity._embedded.employees.map(employee => {
				return client({
					method: 'GET',
					path: employee._links.self.href
				})
			});
		}).then(employeePromises => {
			return when.all(employeePromises);
		}).then(employees => {
			this.setState({
				page: this.page,
				employees: employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: this.state.pageSize,
				links: this.links
			});
		});
	}//...E.refreshCurrentPage(message).websocket-handlers[].added since Part04.

	
//...render : 화면에 컴포넌트를 그리는 API 임.
//...state 가 변경되면, render() 이 호출됨.	
	render() {
		return (
			<div>
				<CreateDialog attributes={this.state.attributes} 
				              onCreate={this.onCreate}/>
				<EmployeeList employees={this.state.employees}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  page={this.state.page} /*...added since Part04.*/
							  attributes={this.state.attributes} /*...added since Part03.*/
							  onUpdate={this.onUpdate} /*...added since Part03.*/
							  onNavigate={this.onNavigate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>			
		)
	}
}
//...E.App___________________________________________________________________


//...CreateDialog 리액트 컴포넌트는 오직 하나의 인스턴스만 있으므로 
//   EmployeeList 의 위에 놓임.
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}//...E.constructor(props).


/*...기본 이벤트 처리를 막음.
	동일한 JSON 스키마 attribute 속성을 이용하여 각 <input> 을 찾음.
	ReactDOM.findDOMNode(this.refs[attribute]) 를 이용하여 각각의 <input> 을 찾음.
	this.refs : name 을 이용하여 특정한 React 컴포넌트를 잡는 방법임.
	단지 가상 DOM 컴포넌트를 얻음.
	실제 DOM 엘리먼트를 얻기 위해서는 React.findDOMNode() 를 이용함.
	최상위 App.onCreate 내부에 있는 onCreate() 콜백을 호출함.*/	
	handleSubmit(e) {
		e.preventDefault();
		var newEmployee = {};
		this.props.attributes.forEach(attribute => {
			newEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newEmployee);
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = ''; // clear out the dialog's inputs
		});
		window.location = "#";// Navigate away from the dialog to hide it.
	}//...E.handleSubmit(e).


//...attributes 속성에서 발견된 JSON 스키마 데이터에 대한 맵핑을 해서 이를 
//	<p><input></p> 엘리먼트 배열형태로 변환함.
	render() {
		var inputs = this.props.attributes.map(attribute =>
//...key : React 가 복수의 자식 노드를 구분하는데 필요한 단순한 텍스트 기반의 항목 필드임.
//   ref : React 는 name 이 아닌 ref 로 특정한 DOM 노드를 가지는 메커니즘임.
//...★CreateDialog  콤포넌트가 App 콤포넌트로 부터 건네받은 속성 attributes 는
//   App 콤포넌트의  loadFromServer(pageSize) 함수에서 schema.entity 의 값에서
//   schema.properties 의 값을 Object.keys 로  받은 값이고, 실제 DOM 에 접근하기 위해서
//   render 부분에서  각 input 태그의 식별값 용도인 ref 에 attribute 를 담고,
//   handleSubmit 은 그렇게 생성된 input 태그의 실제 DOM 엘리먼트를 얻기 위해
//   React.findDOMNode() 를 사용하여 얻음.
			<p key={attribute}>
				<input type="text" placeholder={attribute} 
				                   ref={attribute} 
				                   className="field" />
			</p>
		);

//...최상위의 div 는 대화상자를 여는 버튼에 관한 앵커 태그임.
//   내장된 id="modalDialog" 인 div 는 감춰진 대화상자임.
//	 이 예제에서는 자바스크립트가 아닌 순수한 HTML5 와 CSS3 를 사용함.
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

}
//...E.CreateDialog______________________________________________________________


/*
 * ...CreateDialog 콤포넌트와 유사하게 handleSubmit() 함수와 render() 함수를 가짐.
 *    UpdateDialog 리액트 컴포넌트는 최상위 레벨의 onUpdate 함수와 연결되도록 지정되어 있음.
 *    UpdateDialog 는 특정한 개별 employee 와 직접적으로 묶여져 있어서, 
 *    Employee 리액트 컴포넌트 아래에 플러그인 되어 있음.
 */
class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}//...E.constructor(props).

/*
 * ...제출 버튼은 컴포넌트의 handleSubmit() 과 연결되어 있음.
 *    편리하게 ReactDOM.findDOMNode() 를 이용하여 React refs 를 이용하는 세부사항 팝업을 추출함.
 *    입력값이 추출되고 updatedEmployee 객체 안에 로딩되고,
 *    최상위의 onUpdate() 메서드가 촉발됨.
 *    이것은 상위 레벨의 컴포넌트들에서 하위 레벨로 밀어넣는 함수를 호출하는 
 *    React 스타일의 단방향 바인딩을 계속하게 함.
 *    이런 방식으로 state 는 계속 최상위에서 관리됨.
 *    
 */	
	handleSubmit(e) {
		e.preventDefault();
		var updatedEmployee = {};
		this.props.attributes.forEach(attribute => {
			updatedEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onUpdate(this.props.employee, updatedEmployee);
		window.location = "#";
	}//...E.handleSubmit(e).

/*
 * ...CreateDialog 컴포넌트처럼 동일한 CSS/HTML 특성을 이용해서 대화상자를 보이고 숨김.
 *    JSON 스키마 배열 속성을 HTML input 배열로 변환함.
 *    필드들이 this.props.employee 로 로딩되는 점이 CreateDialog 컴포넌트의 필드들은 
 *    비어있다는 점에서 한가지 차이점임.
 *    id 필드도 CreateDialog 컴포넌트와 다르게 만들어짐.
 *    전체 UI 에 있어서 오직 하나의 CreateDialog 만 있으나, UpdateDialog 링크는 각 row 별로 보임.
 *    그래서, id 필드는 'self link' 의 URI 에 근거함.
 *    이것은 양쪽 div 엘리먼트의 React key 와 HTML 앵커 태그 그리고 숨겨진 팝업에도 사용됨.
 *    
 *     
 */
	render() {
		var inputs = this.props.attributes.map(attribute =>
				<p key={this.props.employee.entity[attribute]}>
					<input type="text" placeholder={attribute}
						   defaultValue={this.props.employee.entity[attribute]}
						   ref={attribute} 
					       className="field" />
				</p>
		);

		var dialogId = "updateEmployee-" + this.props.employee.entity._links.self.href;

		return (
/*...before :
            <div key={this.props.employee.entity._links.self.href}>
...after :*/
			<div>				
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
	}//...E.render().

};
//...E.UpdateDialog.added since Part03___________________________________________


class EmployeeList extends React.Component{

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
//...이벤트가 계층으로 부푸는것을 중지시킴.			
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
			ReactDOM.findDOMNode(this.refs.pageSize).value =
				pageSize.substring(0, pageSize.length - 1);
		}
	}//...E.handleInput(e).	
	
	render() {

{/*...S.added since Part 4. */}
	var pageInfo = this.props.page.hasOwnProperty("number") ?
					<h3>Employees - Page {this.props.page.number + 1} of {this.props.page.totalPages}</h3> 
					: null;
{/*...E.added since Part 4. */}
					
{/*...employee._links.self.href 와 employee 로 부터 값을 받음.	
//   (http://localhost:8080/api/employees/1)
//...★Whenever you work with Spring Data REST, 
//		 the self link IS the key for a given resource. 
//   React needs a unique identifer for child nodes, 
//		 and _links.self.href is perfect.*/}
		var employees = this.props.employees.map(employee =>
			<Employee key={employee.entity._links.self.href} 
			          employee={employee}
					  attributes={this.props.attributes} /*...added since Part03.*/
					  onUpdate={this.props.onUpdate} /*...added since Part03.*/
	                  onDelete={this.props.onDelete}/>
		);
		
//...it still transforms this.props.employees into an array of <Element /> components. 
//   Then it builds up an array of navLinks, an array of HTML buttons.
//...HAL 실행결과 나타난 link 에 해당하는 주소 속성값 유무에 따라 해당 버튼을 만드는 것 같음.		
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
				{pageInfo} {/*...added since Part 4. */}
				<input ref="pageSize" defaultValue={this.props.pageSize} 
									  onInput={this.handleInput}/>
				<table>
					<tbody>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Description</th>
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
}
//...E.EmployeeList__________________________________________________________


class Employee extends React.Component{

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
{/*...최상위 레벨의 onUpdate() 함수와 링크된 UpdateDialog React 컴포넌트를 기존 컴포넌트의
 *    레이아웃 안에 넣음.
 *    UpdateDialog 컴포넌트는 각 employee 와 직접적으로 연결되므로 Employee React 컴포넌트
 *    아래에 배치함.
 * ...당신은 collection 자원을 이용하는 것에서 개별 자원을 이용하는 것으로 전환함.
 *    empoyee 레코드에 대한 필드들은 현재 this.props.employee.entity 에서 발견됨.
 *    this.props.employee.entity 는 ETags 를 발견할 수 있는 this.props.employee.headers 에
 *    접근하게 해줌.
 * ...★ .entity 와 .headers 구조는 오직 REST 라이브러리 선택인 rest.js 를 사용할 때 적합함.
 *    만약 다른 라이브러리를 사용하면, 필요에 맞게 변경해야 할 수 있음.*/}				
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
//...E.render()______________________________________________________________

