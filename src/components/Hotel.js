
import React from 'react';
import { Link }  from 'react-router';

import Header from './Header';
import Footer from './Footer';

export default React.createClass({
  getInitialState() {
    return {
      price: 0,
      hotel: {manager: {}, rooms: []},
      arrival: this.date('today'),
      departure: this.date('tomorrow')
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
    fetch('http://localhost:8080/hotels/'+this.props.params.hotelid)
    .then(function(response) {
      return response.json()
    }).then(function(json) {
      if (that.isMounted()) that.setState({hotel: json});
      console.log('parsed json', json)
    }).catch(function(ex) {
      console.log('parsing failed', ex)
    })
  },
  componentDidMount() {
    console.log('sdafsdf');
    console.log(this.state.arrival);
  },
  updatePrice: function(e) {
    this.setState({price: e.target.options[e.target.selectedIndex].getAttribute('data-price')});
  },
  render: function() {
    const hotel = this.state.hotel;
    const manager = this.state.hotel.manager;
    const roomOpts = this.state.hotel.rooms.map(function (room) {
      return <option data-price={room.price} value={room.id}>{room.type}</option>
    });
    const room = this.state.hotel.rooms.map(function (room) {
      return (
        <tr>
          <td text="room.type.name">{room.type}</td>
          <td text="room.price+'€'">{room.price} €</td>
        </tr>
      );
    });
    return (
      <div>
        <Header/>
        <div className="hotel-cover" style={{backgroundImage: 'url(https://images.unsplash.com/photo-1447722939828-559fee94b1f5?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&s=bb08de9862897259abd5c17ffa75f085)'}}> </div>
        <div className="container row">
          <div className="seven columns">
            <div>
              <p className="clearmargin text-center isle-1-v">
                <span className={'star-'+hotel.rating}></span>
                <br/>
                <span className="h3 thin uppercase">
                  {hotel.name}
                </span>
              </p>
            </div>
            <h5 className="clearmargin thin">
              Category
            </h5>
            <p><span>{hotel.category}</span></p>
            <h5 className="clearmargin thin">
              Adress
            </h5>
            <p> <span>{hotel.address}</span></p>
            <h5 className="clearmargin thin">
              Photos
            </h5>
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <div className="photo-thumb"><img src="https://placehold.it/80x80"/> </div> 
            <h4 className="thin">Manager </h4>
            <div className="media comment">
              <img src="http://www.sustainablebrands.com/sites/default/files/imagecache/100x100/imagecache/thumbnail_img/profilepics/picture-40780.jpg" alt="" className="avatar media__img"/>
              <div className="media__body">
                <h5><span>{manager.name}</span></h5>
                email: <span>{manager.email}</span>
              </div>
            </div>
          </div>
          <div className="five columns">
            <br/>
            <form action="'/hotels/'+hotel.id+'/bookings'" method="POST">
              <label>Check-in</label>
              <input ref="arrival" name="arrival" className="clear" defaultValue={this.state.arrival} type="date"/>
              <label>Check-out</label>
              <input ref="departure" name="departure" className="clear" defaultValue={this.state.departure} type="date"/>
              <label>Room type</label>
              <select onChange={this.updatePrice} name="roomtype">
                <option disabled>Room type</option>
                {roomOpts}
              </select>
              {this.state.price !== 0 ?
                <h4>Preço: <span id="price"></span>{this.state.price} €</h4>
                :
                <h4></h4>
                }
              <button className="button button-primary" type="submit">Book</button>
            </form>
          </div>
        </div>
        <div className="container">
          <hr/>
          <h3 className="thin">Rooms</h3>
          <table className="u-full-width">
            <thead>
              <tr>
                <th>Room Type</th>
                <th>Price</th>
              </tr>
            </thead>
            <tbody>
              {room}
            </tbody>
          </table>
        </div>
        <Footer/>
      </div>
    );
  }
});
