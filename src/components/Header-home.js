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
        <div className="cover">
          <div className="container">
            <div className="row">
              <Link to="/"><h3 className="logo six columns">Hotelr</h3></Link>
              {logged ? (
                <div className="login six columns">
                  <Link style={{paddingRight: '5px'}} to="/aindanaosei">{auth.getUser().name}</Link>
                  •
                  <Link style={{paddingLeft: '5px'}} to="/logout">Log out</Link>
                </div>
                ) : (
                <div className="login six columns">
                  <Link to="/login">Log in</Link>
                </div>
                )}
              </div>
              <p className="copy">
                Find the perfect hotel with us
                <br/>
                <Link to={`/hotels`} className="button">Start now</Link>
              </p>
            </div>
          </div>
        </header>
    );
  }
});
