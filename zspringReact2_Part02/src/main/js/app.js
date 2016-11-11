'use strict';

const React = require('react');
const ReactDOM = require('react-dom')
const client = require('./client');

const follow = require('./follow'); // function to hop multiple links by "rel"

//...유일하게 하드코딩할 경로임.
const root = '/api';
//...E.const_____________________________________________________________________






class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {employees: [],
//...S.PagingAndSortingRepository 사용으로 추가함.
					  attributes: [], 
					  pageSize: 2, 
					  links: {}};
		
		this.updatePageSize = this.updatePageSize.bind(this);
		this.onCreate = this.onCreate.bind(this);
		this.onDelete = this.onDelete.bind(this);
		this.onNavigate = this.onNavigate.bind(this);
//...E.PagingAndSortingRepository 사용으로 추가함.		
	}

	// tag::follow-2[]
	loadFromServer(pageSize) {
//...follow.js 함수 사용으로 root 에서 바로 시작해서 필요한 곳으로 네비게이트 가능함.
//...client : REST 호출을 하는데 사용되는 이용되는 객체임.
//   root : 시작하는 root URI.
//   an array of relationships to navigate along. Each one can be a string or an object.
//   The array of relationships can be as simple as ["employees"], 
//   meaning when the first call is made, look in _links for the relationship (or rel) 
//   named employees. 
//   Find its href and navigate to it. 
//   If there is another relationship in the array, rinse and repeat.		
		follow(client, root, [
//...'?size=<pageSize>' 는 rel 에 끼울 수 있는 쿼리 변수임.
			{rel: 'employees', params: {size: pageSize}}]
		).then(employeeCollection => {
//...Part01 에서는 componentDidMount() 내부에서 로딩이 이뤄졌음.
//...Data 는 rest.js 인자인 client 를 통해서 로딩됨.
//   '/api/employees' 경로로 조회가 이뤄지면 내부의 done() 이 호출됨.
//   HAL 문서인 'response.entity._embedded.employees'(localhost:8080/api/employees 결과) 
//   에 근거해서 상태를 설정함.
//...Part02 에서는 페이지 크기가 변경될 때 전체 목록을 reload 할 수 있도록
//   componentDidMount() 에서 client() 를 loadFromServer() 로 옮겨왔음.
			return client({
				method: 'GET',
				path: employeeCollection.entity._links.profile.href,
				headers: {'Accept': 'application/schema+json'}
			}).then(schema => {
				this.schema = schema.entity;
				return employeeCollection;
			});
		}).done(employeeCollection => {
			this.setState({
				employees: employeeCollection.entity._embedded.employees,
				attributes: Object.keys(this.schema.properties),
				pageSize: pageSize,
				links: employeeCollection.entity._links});
		});
	}
	// end::follow-2[]

	// tag::follow-1[]
	componentDidMount() {
		this.loadFromServer(this.state.pageSize);
	}
	// end::follow-1[]

	render() {
		return (
			<div>
				<EmployeeList employees={this.state.employees}
							  links={this.state.links}
							  pageSize={this.state.pageSize}
							  onNavigate={this.onNavigate}
							  onDelete={this.onDelete}
							  updatePageSize={this.updatePageSize}/>
			</div>
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

