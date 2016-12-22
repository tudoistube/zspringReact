import React from 'react';
//import {render} from 'react-dom'; //...방법1.
import ReactDOM from 'react-dom'; //...방법2.
/*...E.by12p.*/

/*
let zmessage = '${zmessage}';

if (zmessage == 'Merry Christmas^_____^!!!') {
  alert("receive data from server-side.");
}*/

// Parent Component
class HiWorld extends React.Component { //...by12p.ref434p.
//class HiWorld extends Component {
  render() {
    return (
        <h1>Hi, {this.props.zmessage}</h1>
    );
  }
}

//render(<HiWorld />, document.getElementById('zroot')); //...방법1.
ReactDOM.render(<HiWorld zmessage={zmessage}/>, document.getElementById('zroot')); //...방법2.
