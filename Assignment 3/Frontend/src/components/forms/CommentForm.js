import React from "react";

function CommentForm({ serverURL, loggedIn, fetchAll, setInfoState, itemId }) {
    const [input, setInput] = React.useState();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!input) return;

        await addComment();
    };
    const changeData = (e) => {
        setInput(e.target.value);
    };
    const addComment = async () => {
        console.log(itemId);

        const add = async () => {
            await fetch(serverURL + `/comment/create/${itemId}`, {
                method: "POST",
                body: input,
                headers: {
                    token: loggedIn.token,
                },
            }).catch((error) => {
                setInfoState(error);
            });
        };
        await add();

        await fetchAll("/post/all");
        setInput(""); // Clear form
    };

    return (
        <div className="postBox">
            <form className="width100perc" onSubmit={handleSubmit}>
                <textarea
                    name="comment"
                    value={input}
                    rows="5"
                    cols="25"
                    placeholder="Type your comment here..."
                    onChange={changeData}
                />
                <button className="submit comment" type="submit">
                    COMMENT
                </button>
            </form>
        </div>
    );
}

export default CommentForm;
