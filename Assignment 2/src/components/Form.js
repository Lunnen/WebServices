import React from "react";

function Form({ serverURL, loggedIn, fetchAll, setInfoState, infoState }) {
    const defaultInputString = { name: "", desc: "", price: "" };
    const [input, setInput] = React.useState(defaultInputString);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!input.name || !input.desc || !input.price) return;

        await addItem(input);

        setInput(defaultInputString);
    };
    const changeData = (e) => {
        setInput({ ...input, [e.target.name]: e.target.value });
    };

    /* adds new products to list -> used by Form.js */
    const addItem = async (props) => {
        const add = async () => {
            await fetch(serverURL + "/product/create", {
                method: "PUT",
                body: JSON.stringify({
                    name: props.name,
                    description: props.desc,
                    price: props.price,
                }),
                headers: {
                    token: loggedIn,
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    return response.text();
                })
                .then((responseAgain) => {
                    setInfoState(responseAgain);
                })
                .catch((error) => {
                    setInfoState(error);
                });
        };
        await add();

        await fetchAll("/product/all");
    };

    return (
        <div className="container">
            <form className="search" onSubmit={handleSubmit}>
                {infoState ? (
                    <h4 style={{ color: "#ff0000" }}>{infoState}</h4>
                ) : (
                    ""
                )}

                <h3>Add new product here:</h3>
                <input
                    type="text"
                    className="input"
                    name="name"
                    value={input.name}
                    placeholder="Product Name"
                    onChange={changeData}
                />
                <input
                    type="text"
                    className="input"
                    name="desc"
                    value={input.desc}
                    placeholder="Description"
                    onChange={changeData}
                />
                <input
                    type="text"
                    className="input"
                    name="price"
                    pattern="[0-9]*"
                    value={input.price}
                    placeholder="Price (as Integer)"
                    onChange={changeData}
                />
                <button className="submit" type="submit">
                    Add Product
                </button>
            </form>
        </div>
    );
}

export default Form;
