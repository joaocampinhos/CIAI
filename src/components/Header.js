import React from 'react';
import { Link }  from 'react-router';
import auth from '../services/auth';

export default React.createClass({
  render: function() {
    const logged = auth.loggedIn()
    return (
      <header>
        <div className="container">
          <div className="nav row">
            <a href="/"><h3 className="logo six columns">Hotelr</h3></a>
            <div className="logged six columns">
              {logged ? (
                <Link to="/logout">Log out</Link>
                ) : (
                <Link to="/login">Log in</Link>
                )}
              </div>
            </div>
          </div>
        </header>
    );
  }
});
