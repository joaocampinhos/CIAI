var home = function() {
  return (
    <div>
    <h1>Homepage</h1>
    <li><Link to={`/hotels`}>hotels</Link></li>
    <li><Link to={`/login`}>login</Link></li>
    </div>
  );
};

module.exports = {
  home: home
};
