import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Route, Link, History } from 'react-router';
import createBrowserHistory from 'history/lib/createBrowserHistory'

// useless
import PoweredBy from './components/Powered-by';
import About from './components/About';

import Home from './components/App';
import Hotels from './components/Hotels';
import Hotel from './components/Hotel';
import Login from './components/Login';

import Messages from './components/Messages';

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

  componentDidUpdate() {
    const { location } = this.props
    console.log(this.props.location.action);
    //console.log(this.props.location.state);
    if (location.state && location.action === 'REPLACE') {
      if (location.state.message !== this.state.message) {
        console.log(location.state.message);
        console.log(this.state.message);
        console.log('-------------------------------');
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

/*
const App = React.createClass({


  render() {
    return (
      <div>
        <ul>
          <li>
            {this.state.loggedIn ? (
              <Link to="/logout">Log out</Link>
              ) : (
              <Link to="/login">Sign in</Link>
              )}
            </li>
            <li><Link to="/about">About</Link></li>
            <li><Link to="/dashboard">Dashboard</Link> (authenticated)</li>
          </ul>
          {this.props.children}
        </div>
    )
  }
})
*/

function requireAuth(nextState, replaceState) {
  if (!auth.loggedIn())
    replaceState({ nextPathname: nextState.location.pathname }, '/login')
}

const Logout = React.createClass({
  mixins: [ History ],

  componentDidMount() {
    auth.logout()
    console.log(this.props.location);
    this.history.replaceState({message: {type: 'info', value: 'Logout successful.'}}, this.props.location.pathname);
  },

  render() {
    return <p>You are now logged out</p>
  }
})

ReactDOM.render(
    <Router history={createBrowserHistory()}>
      <Route component={App}>
        <Route path="/" component={Home}/>
        <Route path="login" component={Login}/>
        <Route path="logout" component={Logout}/>
        <Route path="hotels" component={Hotels}/>
        <Route path="hotels/:hotelid" component={Hotel}/>
      </Route>
  </Router>
  , document.getElementById('content')
);

/* <Route path="hotels" onEnter={requireAuth} component={Hotels}/> */
