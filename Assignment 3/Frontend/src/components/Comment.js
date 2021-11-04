import React from "react";

// Component imports
import TimeService from "./services/TimeService";
import rSweden from "./img/rSweden.png";

function Comments({ theComment, loggedIn }) {
    const { id, comment, creator, create_date } = theComment;
    const { username } = loggedIn;

    let randomIcon =
        "https://www.redditstatic.com/avatars/defaults/v2/avatar_default_" +
        Math.floor(Math.random() * 8) +
        ".png";

    console.log(creator + " " + username);

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
                    </div>
                    <p>{comment}</p>
                </div>
            </div>
        </>
    );
}
export default Comments;
