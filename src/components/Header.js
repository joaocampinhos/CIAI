import React from 'react';
import { Link }  from 'react-router';
import auth from '../services/auth';

export default React.createClass({
  render: function() {
    const logged = auth.loggedIn()
    if (logged) {
      const user = auth.getUser();
    }
    return (
      <header>
        <div className="container">
          <div className="nav row">
            <Link to="/"><h3 className="logo six columns">Hotelr</h3></Link>
              {logged ? (
            <div className="logged six columns">
                <Link style={{paddingRight: '10px'}} to="/dashboard">{auth.getUser().name}</Link>
                <Link className="button" to="/logout">Log out</Link>
              </div>
                ) : (
            <div className="logged six columns">
                <Link className="button" to="/login">Log in</Link>
              </div>
                )}
            </div>
          </div>
        </header>
    );
  }
});
