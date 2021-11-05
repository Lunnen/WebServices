import React from "react";

// Component imports
import TimeService from "./services/TimeService";
import rSweden from "./img/rSweden.png";

// MUI imports
import HighlightOffIcon from "@mui/icons-material/HighlightOff";
import { IconButton } from "@material-ui/core";

function Comments({
    theComment,
    loggedIn,
    serverURL,
    itemId,
    setInfoState,
    fetchAll,
}) {
    const { id, comment, creator, create_date } = theComment;
    const { username, token } = loggedIn;

    let randomIcon =
        "https://www.redditstatic.com/avatars/defaults/v2/avatar_default_" +
        Math.floor(Math.random() * 8) +
        ".png";

    const deleteComment = () => {
        const requestOptions = {
            method: "DELETE",
            headers: {
                token,
            },
        };

        fetch(serverURL + `/comment/delete/${itemId}/${id}`, requestOptions)
            .then((response) => {
                return response.text();
            })
            .then((data) => {
                if (data) setInfoState(data);
            })
            .catch((error) => {
                setInfoState(error);
            })
            .then(() => {
                fetchAll("/post/all");
            });
    };

    return (
        <>
            <div className="item-container">
                <div className="item-wrapper">
                    <div
                        className="top-item-text"
                        style={{ borderBottom: "1px dotted black" }}
                    >
                        {creator === username ? (
                            <img
                                height="36.4px"
                                src={rSweden}
                                alt="userIcon"
                                style={{ borderRadius: "30rem" }}
                            />
                        ) : (
                            <img
                                height="36.4px"
                                src={randomIcon}
                                alt="userIcon"
                                style={{ borderRadius: "30rem" }}
                            />
                        )}

                        <h5 style={{ fontWeight: "bold" }}>{creator}</h5>
                        <span style={{ marginLeft: "2px" }}>
                            • {TimeService.timeDifference(create_date)}
                        </span>
                        {creator === username ? (
                            <IconButton
                                onClick={() => {
                                    if (
                                        window.confirm(
                                            "Are you sure you wish to REMOVE this post?"
                                        )
                                    ) {
                                        deleteComment();
                                    }
                                }}
                            >
                                <HighlightOffIcon />
                            </IconButton>
                        ) : (
                            ""
                        )}
                    </div>
                    <p>{comment}</p>
                </div>
            </div>
        </>
    );
}
export default Comments;
