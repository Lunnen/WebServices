import React from "react";

// Module imports
import FavoriteOutlinedIcon from "@mui/icons-material/FavoriteOutlined";

function Item({
    item,
    index,
    allItems,
    fetchAll,
    serverURL,
    loggedIn,
    favItems,
    setInfoState,
    infoState,
}) {
    const addToFavorite = (index) => {
        const chosenItem = allItems[index].name;

        // Check if value already exist in favorites, and if so deny the right to add
        if (!favItems.some((el) => el.name === chosenItem)) {
            const userLoggedin = loggedIn;

            const requestOptions = {
                method: "PUT",
                headers: {
                    token: userLoggedin,
                },
                body: chosenItem,
            };

            fetch(serverURL + "/product/add-favorite", requestOptions)
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
                    fetchAll("/product/favorites");
                });
        }
    };

    return (
        <div className="item">
            <h4>{item.name}</h4>
            <p>{item.description}</p>
            <p>{item.price}</p>

            <div>
                <button
                    title="Add to favorites"
                    onClick={() => addToFavorite(index)}
                >
                    <FavoriteOutlinedIcon />
                </button>
            </div>
        </div>
    );
}

export default Item;
