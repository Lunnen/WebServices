import React, { useEffect } from "react";

// Module imports
import { ArrowDownward, ArrowUpward, CommentSharp } from "@material-ui/icons/";
import { IconButton } from "@material-ui/core";
import DeleteOutlineOutlinedIcon from "@mui/icons-material/DeleteOutlineOutlined";
import EditIcon from "@mui/icons-material/Edit";

// Component imports
import TimeService from "./services/TimeService";
import rSweden from "./img/rSweden.png";
import Comment from "./Comment";
import CommentForm from "./forms/CommentForm";

function Item({
    item,
    index,
    fetchAll,
    serverURL,
    loggedIn,
    setInfoState,
    setSpecificPost,
    setView,
    view,
}) {
    /* useStates */
    const [voteType, setVoteType] = React.useState(0);
    const [editMode, setEditMode] = React.useState(false);
    const [editInput, setEditInput] = React.useState(item.description);

    // Destructured objects
    const { comments } = item;
    const { token } = loggedIn;

    useEffect(() => {
        fetchBeenVoted();
    }, []);

    const changeData = (e) => {
        setEditInput(e.target.value);
    };

    /* FETCH Start */
    const fetchBeenVoted = () => {
        const requestOptions = {
            method: "GET",
            headers: {
                token,
            },
        };

        fetch(serverURL + `/post/getMyVotedPosts/${item.id}`, requestOptions)
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return null;
                }
            })
            .then((data) => {
                if (data !== voteType) setVoteType(data);
            })
            .catch((error) => {
                setInfoState(error);
            });
    };

    const voteOnPost = (voteType) => {
        if (!voteType) return;

        const requestOptions = {
            method: "PUT",
            headers: {
                token,
            },
        };

        fetch(
            serverURL + `/post/vote/${item.id}?vote=${voteType}`,
            requestOptions
        )
            .then((response) => {
                if (response.status === 200) {
                    return response.text();
                } else {
                    return null;
                }
            })
            .then((data) => {
                if (data) setInfoState(data);
            })
            .catch((error) => {
                setInfoState(error);
            })
            .then(() => {
                fetchAll("/post/all");
                fetchBeenVoted();
            });
    };

    const deletePost = () => {
        const requestOptions = {
            method: "DELETE",
            headers: {
                token,
            },
        };

        fetch(serverURL + `/post/delete/${item.id}`, requestOptions)
            .then((response) => {
                if (response.status === 200) {
                    return response.text();
                } else {
                    return null;
                }
            })
            .then((data) => {
                if (data) setInfoState(data);
            })
            .catch((error) => {
                setInfoState(error);
            })
            .then(() => {
                fetchAll("/post/all");
                fetchBeenVoted();
            });
    };

    const fetchEditPost = () => {
        const requestOptions = {
            method: "PATCH",
            headers: {
                token,
            },
            body: editInput,
        };

        fetch(serverURL + `/post/update/${item.id}`, requestOptions)
            .then((response) => {
                if (response.status === 200) {
                    console.log(response.text());
                } else {
                    return null;
                }
            })
            .then(() => {
                fetchAll("/post/all");
            })
            .catch((error) => {
                setInfoState(error);
            });
    };

    const reminderToLogin = (inputTextHere) => {
        return (
            <div className="item-container">
                <h2
                    style={{
                        margin: "0 auto",
                        padding: "0.25rem",
                    }}
                >
                    Login to {inputTextHere}
                </h2>
            </div>
        );
    };

    return (
        <div className="item-container">
            <div className="post-vote-wrapper">
                <IconButton
                    className="post-up-vote"
                    onClick={() => voteOnPost(1)}
                >
                    <ArrowUpward
                        color={voteType === 1 ? "secondary" : "inherit"}
                    />
                </IconButton>
                <div className="post-vote-value">{item.vote_value}</div>
                <IconButton
                    className="post-down-vote"
                    onClick={() => voteOnPost(-1)}
                >
                    <ArrowDownward
                        color={voteType === -1 ? "primary" : "inherit"}
                    />
                </IconButton>
            </div>
            <div className="item-wrapper">
                <div>
                    <div className="top-item-text">
                        <img height="20px" src={rSweden} alt="rSweden" />
                        <span style={{ fontWeight: "bold" }}>r/sweden</span>
                        <p id="creator-info">
                            • Posted by u/{item.creator}{" "}
                            {TimeService.timeDifference(item.create_date)}
                        </p>
                        {item.creator === loggedIn.username &&
                        view === "showSpecificPost" ? (
                            <>
                                <IconButton
                                    className="edit-button"
                                    style={{ gap: "5rem" }}
                                    onClick={() => {
                                        setEditMode(!editMode);
                                    }}
                                >
                                    <EditIcon style={{ float: "right" }} />
                                </IconButton>
                                <IconButton
                                    className="delete-button"
                                    style={{ gap: "5rem" }}
                                    onClick={() => {
                                        if (
                                            window.confirm(
                                                "Are you sure you wish to REMOVE this post?"
                                            )
                                        ) {
                                            deletePost();
                                        }
                                    }}
                                >
                                    <DeleteOutlineOutlinedIcon
                                        style={{ float: "right" }}
                                    />
                                </IconButton>
                            </>
                        ) : (
                            ""
                        )}
                    </div>

                    <div className="post-title-text">
                        <h4>{item.title}</h4>
                    </div>
                    <div
                        className={
                            view !== "showSpecificPost"
                                ? "post-description"
                                : "post-description specificPost-description"
                        }
                        onClick={() => {
                            if (view !== "showSpecificPost") {
                                setSpecificPost(item.id);
                                setView("showSpecificPost");
                            }
                        }}
                    >
                        {editMode ? (
                            <>
                                <textarea
                                    onChange={changeData}
                                    value={editInput}
                                />
                                <button
                                    className="saveButton"
                                    onClick={() => {
                                        fetchEditPost();
                                        setEditMode(false);
                                    }}
                                >
                                    save
                                </button>
                                <button
                                    className="cancelButton"
                                    onClick={() => {
                                        setEditMode(false);
                                    }}
                                >
                                    Cancel
                                </button>
                            </>
                        ) : (
                            <p>{item.description}</p>
                        )}
                        {token && view === "showSpecificPost" ? (
                            <CommentForm
                                itemId={item.id}
                                serverURL={serverURL}
                                loggedIn={loggedIn}
                                fetchAll={fetchAll}
                                setInfoState={setInfoState}
                            />
                        ) : token ? (
                            ""
                        ) : (
                            reminderToLogin("comment")
                        )}

                        {comments.length > 0 ? (
                            <>
                                {comments
                                    .sort((a, b) =>
                                        b.create_date.localeCompare(
                                            a.create_date
                                        )
                                    )
                                    .map((theComment, index) => (
                                        <Comment
                                            key={index}
                                            theComment={theComment}
                                            loggedIn={loggedIn}
                                        />
                                    ))}
                            </>
                        ) : (
                            ""
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Item;
