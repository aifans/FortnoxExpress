import React from 'react'
import { Link } from 'react-router-dom'
import endpoints from "../../routing/endpoints";

const Navbar = () => {

    return (
      <nav className="navbar navbar-default" role="navigation">
        <div className="container-fluid">
          <div className="navbar-header">
            <a className="navbar-brand">
              Express
            </a>
          </div>
          <div className="collapse navbar-collapse">
            <ul className="nav navbar-nav">
              <li className="active" data-toggle="tab">
                <Link to={endpoints.Place_Order}>
                  Place An Order
                </Link>
              </li>
              <li data-toggle="tab">
                <Link to={endpoints.List_Order}>
                  Orders History
                </Link>
              </li>
            </ul>
          </div>

        </div>
      </nav>

    );
};

export default Navbar;
