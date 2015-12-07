
import React from 'react';
import { Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({
  getInitialState() {
    return {
      arrival: this.date('today'),
      departure: this.date('tomorrow'),
      hotels: [],
    };
  },
  date: function(date) {
    if (date === 'today') {
      return new Date().toJSON().slice(0,10);
    }
    if (date === 'tomorrow') {
     return new Date(new Date().getTime() + 24 * 60 * 60 * 1000).toJSON().slice(0,10);
    }
  },
  componentWillMount() {
    var that = this;
    fetch('http://localhost:8080/hotels')
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      if (that.isMounted()) that.setState({hotels: json.hotels});
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    })
  },
  render: function() {
    var hotels = 'NOPE';
    if (this.state.hotels.length > 0) {
      var hotels = this.state.hotels.map(function (hotel) {
        console.log(hotel.rating);
        return (
          <div className="media hotel-list">
            <img src="https://placehold.it/150x150" alt="" className="media__img"/>
            <div className="media__body">
              <div className="row">
                <div className="nine columns">
                  <h5><a className="a-none" text="${hotel.name}" href="@{|/hotels/${hotel.id}|}">{hotel.name}</a> <span className={'star-' + hotel.rating}></span></h5>
                  <p className="clearmargin"> Address: <span>{hotel.address}</span></p>
                  <p className="clearmargin"> Category: <span>{hotel.category}</span></p>
                </div>
                <div className="three columns">
                  <div className="right">
                    {hotel.comments.length} comments
                  </div>
                </div>
              </div>
              <div className="row bottomrow">
                <div className="eight columns">
                </div>
                <div className="right four columns">
                  <a href="@{|/hotels/${hotel.id}|}" className="gohotel clearmargin button button-full button-primary">Book now</a>
                </div>
              </div>
            </div>
          </div>
        );
      });
      }
    return (
      <div>
        <Header/>
        <div >
          <form className="clear" method="GET">
            <div className="container">
              <br/>
              <div className="row">
                <div className="four columns">
                  <label>Dates</label>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="arrival" className="clear" defaultValue={this.state.arrival} type="date"/>
                  </div>
                </div>
                <div className="four columns">
                  <div className="isle-1-h">
                    <input name="departure" className="clear" defaultValue={this.state.departure} type="date"/>
                  </div>
                </div>
              </div>
              <br/>
              <hr className="clear"/>
              <br/>
            </div>
            <div className="container">
              <div className="row">
                <div className="four columns">
                  <label>Hotel Name</label>
                </div>
                <div className="eight columns">
                  <div className="isle-1-h">
                    <input type="text"></input>
                  </div>
                </div>
              </div>
              <br/>
              <hr className="clear"/>
              <br/>
            </div>
            <div className="container">
              <div className="row">
                <div className="four columns">
                  <label>Room Type</label>
                </div>
                <div className="eight columns">
                  <div className="isle-1-h">
                    <select name="roomtype" className="clear">
                      <option defaultValue disabled="">Room Type</option>
                    </select>
                  </div>
                </div>
              </div>
              <br/>
            </div>
            <div className="secondary">
              <div className="container isle-1-v">
                <button type="submit">Filter</button>
                <span className="right"><b><span>{this.state.hotels.length}</span> </b>Hotels Found</span>
              </div>
            </div>
          </form>
          <div className="secondary">
            <div className="container">
              {hotels}
            </div>
          </div>
        </div>
      </div>
    );
  }
});
