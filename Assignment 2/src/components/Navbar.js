import React from "react";

// Components
import logo from "./img/logo2.png";
import { Button } from "@material-ui/core";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";

const Navbar = ({ loggedIn, setLoggedIn, serverURL, infoState }) => {
    const logout = () => {
        const userLoggedin = loggedIn;

        const requestOptions = {
            method: "POST",
            headers: {
                token: userLoggedin,
            },
        };

        fetch(serverURL + "/user/logout", requestOptions)
            .catch((error) => {
                console.log(error);
            })
            .then(() => {
                setLoggedIn();
            });
    };

    return (
        <div className="navbar">
            {loggedIn ? (
                <Button
                    variant="Outlined"
                    endIcon={<ExitToAppIcon />}
                    className="logoutButton"
                    onClick={() => logout()}
                >
                    LOGOUT
                </Button>
            ) : (
                ""
            )}
            <img src={logo} alt="Logo" />
            {infoState ? (
                <h4 style={{ color: "#ff0000" }}>
                    Status message: {infoState}
                </h4>
            ) : (
                ""
            )}
        </div>
    );
};

export default Navbar;
