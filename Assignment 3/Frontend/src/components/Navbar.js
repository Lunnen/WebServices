import React from "react";

// MUI imports
import { TextField, IconButton } from "@material-ui/core";
import { SearchOutlined } from "@material-ui/icons";

// Components
import logo from "./img/toplogo.png";
import ExitToAppOutlinedIcon from "@mui/icons-material/ExitToAppOutlined";

const Navbar = ({
    loggedIn,
    setLoggedIn,
    serverURL,
    setView,
    fetchAll,
    setInfoState,
    setSearchedPosts,
}) => {
    // Deconstructed variables
    const { username, token } = loggedIn;

    const [input, setInput] = React.useState();

    const logout = () => {
        const requestOptions = {
            method: "POST",
            headers: {
                token: loggedIn.token,
            },
        };

        fetch(serverURL + "/user/logout", requestOptions)
            .catch((error) => {
                console.log(error);
            })
            .then(() => {
                setLoggedIn({});
                localStorage.removeItem("LocalData");
            });
    };

    const changeData = (e) => {
        setInput(e.target.value);
    };

    const searchPosts = async (e) => {
        e.preventDefault();

        if (!input) return;

        await fetch(serverURL + `/post/search/${input}`, {
            method: "GET",
        })
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    setInfoState(response.status);
                }
            })
            .then((data) => {
                if (data !== null) {
                    setSearchedPosts(data);
                    setView("showSearchedPost");
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };

    return (
        <div className="navbar">
            <a href="/">
                <img height="32px" src={logo} alt="Logo" />
            </a>

            <form onSubmit={searchPosts}>
                <TextField
                    className="search"
                    variant="outlined"
                    name="search"
                    placeholder="Search Reddit"
                    onChange={changeData}
                    InputProps={{
                        startAdornment: (
                            <IconButton>
                                <SearchOutlined onClick={() => searchPosts()} />
                            </IconButton>
                        ),
                    }}
                />
            </form>

            {!token ? (
                <div>
                    <button
                        className="loginButton"
                        onClick={() => setView("login")}
                    >
                        Login / Sign Up
                    </button>
                </div>
            ) : (
                <>
                    <h5 className="userLoggedIn">{loggedIn.username}</h5>
                    <div className="logoutButton" onClick={() => logout()}>
                        <ExitToAppOutlinedIcon />
                        Log Out
                    </div>
                </>
            )}
        </div>
    );
};

export default Navbar;
