
import React from 'react';
import { History, Link }  from 'react-router';

import Messages from './Messages';

export default React.createClass({
  mixins: [ History ],
  getInitialState() {
    return {
      message: {}
    };
  },
  onSubmit: function(e) {
    e.preventDefault();

    var that = this;

    fetch('http://localhost:8080/login', {
      method: 'post',
      body: new FormData(e.target)
    })
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      that.setState({message: json.message});
      that.refs.flash.show();
      //se for com sucesso guarda a sessao e volta a pagina anterior
      //that.history.goBack()
    }).catch(function(err) {})
  },
  render: function() {
    return (
      <div>
        <Messages ref="flash" message={this.state.message}/>
        <div className="container">
          <div className="log-form">
            <a href="/"><img src="src/images/logo.png" className="logo-img"/></a>
          </div>
          <div>
            <form onSubmit={this.onSubmit} name="form">
              <input placeholder="Email" type="text" name="login"/>
              <input placeholder="Password" type="password" name="password"/>
              <button type="submit" className="button-full button-green">Log-in</button>
            </form>
          </div>
        </div>
      </div>
    );
  }
});
