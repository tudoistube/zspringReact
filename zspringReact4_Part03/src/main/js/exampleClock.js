//...State and Lifecycle - React
//   https://facebook.github.io/react/docs/state-and-lifecycle.html
//   Clock 컴포넌트를 만드는 과정을 통해 React 의 state 데이터를 다른 컴포넌트의 props 로 넘기는 과정을 설명함.
class Clock extends React.Component {
  constructor(props){
    super(props);
    this.state = { date : new Date() };
  }
  
  componentDidMount() {
    this.timerID = setInterval(
      () => this.tick(), 1000
    );
  }

  componentWillUnmount() {
    clearInterval(this.timerID);
  }  
  
  tick(){
    this.setState({
      date: new Date()
    });
  }
  
  render(){
    return (
      <div>
        <h1>Hi, JoyWins</h1>
        <FormattedDate date={this.state.date} />
      </div>
    );    
  }
}

function FormattedDate(props) {
  return <h2>Yes, It is {props.date.toLocaleTimeString()}.</h2>;
}

function App() {
  return (
    <div>
      <Clock />
      <Clock />
      <Clock />
    </div>
  );
}


ReactDOM.render( <App />, document.getElementById('root') );

