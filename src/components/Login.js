
import React from 'react';
import { Link }  from 'react-router';
//import Fetch from 'whatwg-fetch';

import Messages from './Messages';

export default React.createClass({
  getInitialState() {
    return {
      message: null
    };
  },
  onSubmit: function(e) {
    e.preventDefault();

    var email = document.getElementsByName('username',e.target)[0].value;
    var password = document.getElementsByName('password',e.target)[0].value;

    fetch('http://localhost:8080/login', {
      method: 'post',
      body: new FormData(e.target)
    })
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
        <Messages message={this.state.message}/>
        <div className="log-form">
          <a href="/"><img src="src/images/logo.png" className="logo-img"/></a>
        </div>
        <div>
          <form onSubmit={this.onSubmit} name="form">
            <input placeholder="Email" type="text" name="username"/>
            <input placeholder="Password" type="password" name="password"/>
            <button type="submit" className="button-green">Log-in</button>
          </form>
        </div>
      </div>
    );
  }
});
