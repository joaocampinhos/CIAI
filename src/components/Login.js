import React from 'react';
import auth from '../services/auth';
import { History, Link }  from 'react-router';

export default React.createClass({

  mixins: [ History ],

  onSubmit(event) {

    event.preventDefault();

    auth.login(new FormData(event.target), (logged, message) => {
      if (!logged) {
        return this.history.replaceState({message: message}, '/login')
      }
      else {
        return this.history.replaceState({message: message}, window.previousLocation.pathname)
      }
    });
  },
  render: function() {
    return (
      <div>
        <div className="container">
          <div className="log-form">
            <Link to="/"><img src="src/images/logo.png" className="logo-img"/></Link>
          </div>
          <div>
            <form onSubmit={this.onSubmit} name="form">
              <input ref="email" placeholder="Email" type="text" name="login"/>
              <input ref="pass" placeholder="Password" type="password" name="password"/>
              <button type="submit" className="button-full button-green">Log-in</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
});
