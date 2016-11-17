'use strict';

//...webpack 을 이용하여 조합을 하므로, 필요한 모듈을 불러옴.
const React = require('react');
const ReactDOM = require('react-dom')
//...client is custom code that configures rest.js to include support for 
//   HAL, URI Templates, and other things. 
//   It also sets the default Accept request header to application/hal+json. 
//   You can read the code here.
const client = require('./client');
//...E.require___________________________________________________________________

//...컴포넌트를 정의하는 것에 기초하는 것이 React 임.
//   하나의 컴포넌트가 여러개의 인스턴스를 담는 부모-자식 관계로 여러 층으로 
//   확장하기에 쉬운 개념임.
//   먼저, 모든 컴포넌트들에 대한 최상위 컨테이너를 갖는 것이 용이함.
//...React.createClass({…​}) : React 컴포넌트를 생성하는 메서드임.
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: []};
	}

//...componentDidMount : React 가 DOM 에 있는 컴포넌트를 렌더링한 후 호출되는 API 임.
//...App 컴포넌트에서, employee 배열이 Spring Data REST 백엔드에 의해서 가져와지고
//   App 컴포넌트의 state 값에 저장이 됨.	
	componentDidMount() {
//...Data 는 rest.js 인자인 client 를 통해서 로딩됨.
//   '/api/employees' 경로로 조회가 이뤄지면 내부의 done() 이 호출됨.
//   HAL 문서인 'response.entity._embedded.employees'(localhost:8080/api/employees 결과) 
//   에 근거해서 상태를 설정함.		
		client({method: 'GET', path: '/api/employees'}).done(response => {
			this.setState({employees: response.entity._embedded.employees});
		});
	}

//...render : 화면에 컴포넌트를 그리는 API 임.
//...state 가 변경되면, render() 이 호출됨.	
	render() {
		return (
			<EmployeeList employees={this.state.employees}/>
		)
	}
}
//...E.App___________________________________________________________________


class EmployeeList extends React.Component{
	render() {
		var employees = this.props.employees.map(employee =>
//...employee._links.self.href 와 employee 로 부터 값을 받음.	
//   (http://localhost:8080/api/employees/1)
//...★Whenever you work with Spring Data REST, 
//	 the self link IS the key for a given resource. 
//   React needs a unique identifer for child nodes, 
//	 and _links.self.href is perfect.
			<Employee key={employee._links.self.href} employee={employee}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Description</th>
					</tr>
					{employees}
				</tbody>
			</table>
		)
	}
}
//...E.EmployeeList__________________________________________________________


class Employee extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.employee.firstName}</td>
				<td>{this.props.employee.lastName}</td>
				<td>{this.props.employee.description}</td>
			</tr>
		)
	}
}
//...E.Employee______________________________________________________________


ReactDOM.render(
	<App />,
	document.getElementById('react')
)
//...E.render()______________________________________________________________

