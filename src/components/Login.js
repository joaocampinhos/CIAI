
import React from 'react';
import { Link }  from 'react-router';
import Fetch from 'whatwg-fetch';

import Messages from './Messages';
import Header from './Header-home';
import Footer from './Footer';

export default React.createClass({
  getInitialState() {
    fetch('/users.json')
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      console.log('parsed json', json)
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    })
  },
  render: function() {
    return (
      <div>
        <div className="log-form">
          <a href="/"><img src="src/images/logo.png" className="logo-img"/></a>
        </div>
        <div>
          <form name="form" method="POST" action="/login">
            <input placeholder="Email" type="text" name="username"/>
            <input placeholder="Password" type="password" name="password"/>
            <button type="submit" className="button-green">Log-in</button>
          </form>
        </div>
      </div>
    );
  }
});
