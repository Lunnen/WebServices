import React from "react";

function Form({ serverURL, loggedIn, fetchAll, setInfoState }) {
    const { token } = loggedIn;

    const defaultInputString = { name: "", desc: "" };
    const [input, setInput] = React.useState(defaultInputString);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!input.name || !input.desc) return;

        await addItem(input);

        setInput(defaultInputString);
    };
    const changeData = (e) => {
        setInput({ ...input, [e.target.name]: e.target.value });
    };

    /* adds new products to list -> used by Form.js */
    const addItem = async (props) => {
        const add = async () => {
            await fetch(serverURL + "/post/add", {
                method: "POST",
                body: JSON.stringify({
                    title: props.name,
                    description: props.desc,
                }),
                headers: {
                    token: loggedIn.token,
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    return response.text();
                })
                .then((data) => {
                    setInfoState(data);
                })
                .catch((error) => {
                    setInfoState(error);
                });
        };
        await add();

        await fetchAll("/post/all");
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
        <>
            {token ? (
                <div className="postBox">
                    <form className="width100perc" onSubmit={handleSubmit}>
                        <input
                            type="text"
                            className="input"
                            name="name"
                            value={input.name}
                            placeholder="Title"
                            onChange={changeData}
                        />
                        <textarea
                            className="input input-desc"
                            name="desc"
                            value={input.desc}
                            placeholder="Description"
                            onChange={changeData}
                        />
                        <button className="submit" type="submit">
                            POST
                        </button>
                    </form>
                </div>
            ) : (
                reminderToLogin("create post")
            )}
        </>
    );
}

export default Form;
