import React from 'react';
import auth from '../services/auth';
import { History, Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({

  mixins: [ History ],

  getInitialState() {
    return {
      bookings: [],
    };
  },
  componentWillMount() {
    var that = this;
    fetch('http://localhost:8080/user/'+auth.getUser().id+'/bookings?cookie='+auth.getToken())
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      if (json.message && json.message.value === "You are not logged in.") {
        auth.logout();
        return that.history.replaceState({message: json.message}, '/login')
      }
      if (that.isMounted()) that.setState({bookings: json.bookings});
    }).catch(function(ex) { })
  },
  date: function(e) {
    var d = new Date(e);
    return d.toJSON().slice(0,10);
  },
  render: function() {
    var that = this;
    const book = this.state.bookings.map(function(b) {
      return (
        <tr>
          <td>{b.hotel.name}</td>
          <td>{that.date(b.arrival)}</td>
          <td>{that.date(b.departure)}</td>
          <td>{b.roomtype.name}</td>
          <td>{b.price}</td>
        </tr>
      );
    });
    return (
      <div>
        <Header/>
        <div className="secondary">
          <div className="container">
            <br/>
            <div className="panel">
              <div className="title">
                Bookings
              </div>
              <div className="body">
                {this.state.bookings.length !== 0 ? (
                <table className="table-dash">
                  <thead>
                    <tr>
                      <th>Hotel</th>
                      <th>Check-in</th>
                      <th>Check-out</th>
                      <th>Room Type</th>
                      <th>Price</th>
                    </tr>
                  </thead>
                  <tbody>
                    {book}
                  </tbody>
                </table>
                ) : (
                <h5 className="clear isle-1-v isle-1-h">You have no bookings</h5>
                )}
              </div>
            </div>
            <br/>
            <Link className="button button-blue" to="/hotels">New Booking</Link>
          </div>
          <br/>
        </div>
        <Footer/>
      </div>
    );
  }
});
