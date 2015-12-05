import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route } from 'react-router';
import createBrowserHistory from 'history/lib/createBrowserHistory'

// useless
import PoweredBy from './components/Powered-by';
import About from './components/About';

import Home from './components/App';
import Hotels from './components/Hotels';
import Hotel from './components/Hotel';
import Login from './components/Login';

//import Auth from './util/Auth';

window.React = React;

var App = React.createClass({
  //LOGIN SHIET
  getInitialState() {
    return {
      loggedIn: true
    }
  },
  updateAuth(loggedIn) {
    this.setState({
      loggedIn: loggedIn
    })
  },
  render: function() {
    return (
      <div>
        {this.props.children}
      </div>
    )
  }
});

ReactDOM.render(
    <Router history={createBrowserHistory()}>
      <Route component={App}>
        <Route path="/" component={Home}/>
        <Route path="login" component={Login}/>
        <Route path="hotels" component={Hotels}/>
        <Route path="hotels/:hotelid" component={Hotel}/>
      </Route>
  </Router>
  , document.getElementById('content')
);
