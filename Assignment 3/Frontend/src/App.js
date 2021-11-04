/* eslint-disable react-hooks/exhaustive-deps */

// Core imports
import React, { useEffect, useState } from "react";
import "./components/css/App.css";

// Components
import Form from "./components/forms/Form";
import Item from "./components/Item";
import Navbar from "./components/Navbar";
import LoginForm from "./components/forms/LoginForm";
import SignupForm from "./components/forms/SignupForm";

// Variables
const serverURL = "http://localhost:8080";

function App() {
    // Item useStates
    const [items, setItems] = useState([]);
    const [specificPost, setSpecificPost] = useState({});
    const [searchedPosts, setSearchedPosts] = useState([]);

    // Variable useStates
    const [init, setInit] = useState(true);
    const [loggedIn, setLoggedIn] = useState({});
    const [infoState, setInfoState] = useState();
    const [view, setView] = useState("default");

    // -------------------------------------------------------
    const fetchAll = async (pathURI) => {
        await fetch(serverURL + pathURI, {
            method: "GET",
        })
            .then((response) => {
                return response.json();
            })
            .then((responseAgain) => {
                if (responseAgain != null) {
                    setItems(responseAgain);
                    setInit(false);
                }
            })
            .catch((error) => {
                console.log(error);
            });
    };
    // -------------------------------------------------------
    useEffect(() => {
        setTimeout(() => {
            setInfoState();
        }, 2500);
    }, [infoState]);

    useEffect(() => {
        fetchAll("/post/all");

        /* Get login-status from storage */
        let save = JSON.parse(localStorage.getItem("LocalData"));
        save != null ? setLoggedIn(save) : setLoggedIn({});
    }, []);

    // -------------------------------------------------------
    /* Single pages - Only used if ternary says so */

    const productItemsHasContent = () => {
        return (
            <>
                {items
                    .sort((a, b) => b.create_date.localeCompare(a.create_date))
                    .map((item, index) => (
                        <Item
                            key={index}
                            index={index}
                            item={item}
                            allItems={items}
                            fetchAll={fetchAll}
                            serverURL={serverURL}
                            loggedIn={loggedIn}
                            setInfoState={setInfoState}
                            setSpecificPost={setSpecificPost}
                            setView={setView}
                        />
                    ))}
            </>
        );
    };

    const nothingYet = () => {
        return <p>Just an empty list!</p>;
    };

    const noSearchResults = () => {
        return (
            <div style={{ marginTop: "5rem" }}>
                <h2>Sorry! no results on that search.</h2>
            </div>
        );
    };
    // -------------------------------------------------------
    /* switchDefault pages */

    const hasBEinfo = () => {
        return (
            <>
                <div className="container">
                    <Form
                        serverURL={serverURL}
                        loggedIn={loggedIn}
                        fetchAll={fetchAll}
                        setInfoState={setInfoState}
                    />
                    {items.length > 0 ? productItemsHasContent() : nothingYet()}
                </div>
            </>
        );
    };

    const noBEinfo = () => {
        return (
            <h1 style={{ marginTop: "3rem", textAlign: "center" }}>
                No info from Backend!
            </h1>
        );
    };

    const showSpecificPost = () => {
        return (
            <>
                <div style={{ marginTop: "3rem" }}>
                    {items
                        .filter((post, index) => post.id === specificPost)
                        .map((item, index) => (
                            <Item
                                key={index}
                                index={index}
                                item={item}
                                allItems={items}
                                fetchAll={fetchAll}
                                serverURL={serverURL}
                                loggedIn={loggedIn}
                                setInfoState={setInfoState}
                                setSpecificPost={setSpecificPost}
                                setView={setView}
                                view={view}
                            />
                        ))}
                </div>
            </>
        );
    };

    const showSearchedPost = () => {
        return (
            <>
                {searchedPosts ? (
                    <div style={{ marginTop: "3rem" }}>
                        {searchedPosts.map((item, index) => (
                            <Item
                                key={index}
                                index={index}
                                item={item}
                                allItems={items}
                                fetchAll={fetchAll}
                                serverURL={serverURL}
                                loggedIn={loggedIn}
                                setInfoState={setInfoState}
                                setSpecificPost={setSpecificPost}
                                setView={setView}
                                view={view}
                            />
                        ))}
                    </div>
                ) : (
                    noSearchResults()
                )}
            </>
        );
    };

    // -------------------------------------------------------

    /* Switch between pages */
    const runMainSwitch = () => {
        switch (view) {
            case "showSpecificPost":
                return <> {showSpecificPost()} </>;
            case "showSearchedPost":
                return <> {showSearchedPost()} </>;
            case "login":
                return (
                    <>
                        <LoginForm
                            serverURL={serverURL}
                            setLoggedIn={setLoggedIn}
                            setInfoState={setInfoState}
                            setView={setView}
                        />
                        <SignupForm
                            serverURL={serverURL}
                            setInfoState={setInfoState}
                        />
                    </>
                );
            default:
                return <>{init ? noBEinfo() : hasBEinfo()}</>;
        }
    };
    /* MAIN PAGE */
    return (
        <>
            <Navbar
                loggedIn={loggedIn}
                setLoggedIn={setLoggedIn}
                serverURL={serverURL}
                infoState={infoState}
                setView={setView}
                fetchAll={fetchAll}
                setInfoState={setInfoState}
                setSearchedPosts={setSearchedPosts}
            />

            <div className="app">
                <div>
                    {infoState ? (
                        <h4
                            className="container"
                            style={{
                                textAlign: "center",
                                marginTop: "3rem",
                                color: "#ff0000",
                            }}
                        >
                            Status message: {infoState}
                        </h4>
                    ) : (
                        ""
                    )}
                    {runMainSwitch()}
                </div>
            </div>
        </>
    );
    // -------------------------------------------------------
}

export default App;
