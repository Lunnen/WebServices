import React from "react";

function FavoriteItem({ item, index, favItem }) {
    return (
        <div className="item">
            <h4>{favItem.name}</h4>
            <p>{favItem.description}</p>
            <p>{favItem.price}</p>
        </div>
    );
}

export default FavoriteItem;
