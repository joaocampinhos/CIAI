import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, Link, History } from 'react-router';
import createBrowserHistory from 'history/lib/createBrowserHistory'

//Pages
import Home from './components/App';
import Hotels from './components/Hotels';
import Hotel from './components/Hotel';
import Login from './components/Login';

//Components
import Messages from './components/Messages';

//Sevices
import auth from './services/auth';

window.React = React;

var App = React.createClass({

  getInitialState() {
    return {
      loggedIn: auth.loggedIn()
    }
  },

  updateAuth(loggedIn) {
    this.setState({
      loggedIn: loggedIn
    })
  },

  componentWillMount() {
    auth.onChange = this.updateAuth
    auth.login()
  },

  componentWillReceiveProps() {
    if (this.props.location.pathname != '/login')
      window.previousLocation = this.props.location
  },

  componentDidUpdate() {
    const { location } = this.props
    if (location.state && location.action === 'REPLACE') {
      if (location.state.message !== this.state.message) {
        this.setState({message: location.state.message});
        this.refs.flash.show();
      }
    }
  },

  render: function() {
    return (
      <div>
        <Messages ref="flash" message={this.state.message}/>
        {this.props.children}
      </div>
    )
  }
});

function requireAuth(nextState, replaceState) {
  if (!auth.loggedIn())
    replaceState({ nextPathname: nextState.location.pathname }, '/login')
}

function authed(nextState, replaceState) {
  if (!window.previousLocation) {
    window.previousLocation = {pathname: '/'}
  }
  if (auth.loggedIn())
    replaceState(null, window.previousLocation.pathname)
}

const Logout = React.createClass({
  mixins: [ History ],

  componentDidMount() {
    auth.logout()
    const { location } = this.props
    const last = window.previousLocation.pathname;
    this.history.replaceState({message: {type: 'info', value: 'Logout successful.'}}, last);
  },

  render() {
    return <p>You are now logged out</p>
  }
})

ReactDOM.render(
    <Router onUpdate={() => window.scrollTo(0, 0)} history={createBrowserHistory()}>
      <Route component={App}>
        <Route path="/" component={Home}/>
        <Route path="login" onEnter={authed} component={Login}/>
        <Route path="logout" component={Logout}/>
        <Route path="hotels" component={Hotels}/>
        <Route path="hotels/:hotelid" component={Hotel}/>
      </Route>
  </Router>
  , document.getElementById('content')
);

