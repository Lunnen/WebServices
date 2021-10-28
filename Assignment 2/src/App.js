/* eslint-disable react-hooks/exhaustive-deps */

// Core imports
import React, { useEffect, useState } from "react";
import "./components/css/App.css";

// Components
import Form from "./components/Form";
import Item from "./components/Item";
import FavoriteItem from "./components/FavoriteItem";
import Navbar from "./components/Navbar";
import LoginForm from "./components/LoginForm";
import SignupForm from "./components/SignupForm";

// Variables
const serverURL = "http://localhost:8080";

function App() {
    // Item useStates
    const [items, setItems] = useState([]);
    const [favItems, setFavItems] = useState([]);

    // Variable useStates
    const [init, setInit] = useState(true);
    const [loggedIn, setLoggedIn] = useState();
    const [infoState, setInfoState] = useState();

    // -------------------------------------------------------
    const fetchAll = async (thisURI) => {
        await fetch(serverURL + thisURI, {
            method: "GET",
            headers: {
                token: loggedIn,
            },
        })
            .then((response) => {
                return response.json();
            })
            .then((responseAgain) => {
                thisURI === "/product/favorites"
                    ? setFavItems(responseAgain)
                    : setItems(responseAgain);
            })
            .catch((error) => {
                console.log(error);
            });

        setInit(false);
    };
    // -------------------------------------------------------
    /* The two useEffects keeping track of things */

    useEffect(() => {
        setTimeout(() => {
            setInfoState();
        }, 2500);
    }, [infoState]);

    /* Runs on every changed user (new loggedIn-data) */
    useEffect(() => {
        //only run if someone is logged in (solves logout-Bug)
        if (loggedIn) {
            fetchAll("/product/all");
            fetchAll("/product/favorites");
        }
    }, [loggedIn]);

    // -------------------------------------------------------
    /* Single pages - Only used if ternary says so */
    const noBEinfo = () => {
        return <h1 style={{ textAlign: "center" }}>No info from Backend!</h1>;
    };

    const favItemsHasContent = () => {
        return (
            <div className="container">
                {favItems.map((favItem, index) => (
                    <FavoriteItem key={index} favItem={favItem} />
                ))}
            </div>
        );
    };

    const productItemsHasContent = () => {
        return (
            <>
                {items.map((item, index) => (
                    <Item
                        key={index}
                        index={index}
                        item={item}
                        allItems={items}
                        fetchAll={fetchAll}
                        serverURL={serverURL}
                        loggedIn={loggedIn}
                        favItems={favItems}
                        setInfoState={setInfoState}
                    />
                ))}
            </>
        );
    };

    const nothingYet = () => {
        return <p>Just an empty list!</p>;
    };
    // -------------------------------------------------------
    /* pickable pages */
    const hasBEinfo = () => {
        return (
            <>
                <div className="container">
                    <h2>Favorites</h2>
                    {favItems.length > 0 ? favItemsHasContent() : nothingYet()}
                </div>

                <div className="container">
                    <h2>List of products</h2>
                    {items.length > 0 ? productItemsHasContent() : nothingYet()}
                </div>
                <Form
                    serverURL={serverURL}
                    loggedIn={loggedIn}
                    fetchAll={fetchAll}
                    setInfoState={setInfoState}
                />
            </>
        );
    };

    const needToLogin = () => {
        return (
            <>
                <LoginForm
                    serverURL={serverURL}
                    setLoggedIn={setLoggedIn}
                    setInfoState={setInfoState}
                />
                <SignupForm serverURL={serverURL} setInfoState={setInfoState} />
            </>
        );
    };
    // -------------------------------------------------------
    /* MAIN PAGE */
    return (
        <div className="app">
            <Navbar
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                serverURL={serverURL}
                infoState={infoState}
            />

            {loggedIn ? (init ? noBEinfo() : hasBEinfo()) : needToLogin()}
        </div>
    );
}

export default App;
