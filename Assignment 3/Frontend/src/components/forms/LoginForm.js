import React from "react";

// MUI
import IconButton from "@material-ui/core/IconButton";
import Visibility from "@material-ui/icons/Visibility";
import InputAdornment from "@material-ui/core/InputAdornment";
import VisibilityOff from "@material-ui/icons/VisibilityOff";
import Input from "@material-ui/core/Input";

function LoginForm({ serverURL, setLoggedIn, setInfoState, setView }) {
    /* Form input handling */
    const defaultInputString = { name: "", password: "" };
    const [input, setInput] = React.useState(defaultInputString);

    const [showPassword, setShowPassword] = React.useState(false);

    const changeData = (e) => {
        setInput({ ...input, [e.target.name]: e.target.value });
    };

    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!input.name || !input.password) return;

        const requestOptions = {
            method: "POST",
            headers: {
                username: input.name,
                password: input.password,
            },
        };

        fetch(serverURL + "/user/login", requestOptions)
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    setInfoState("Invalid credentials! Try again!");
                    return null;
                }
            })
            .then((data) => {
                if (data !== null) {
                    setLoggedIn(data);
                    setView("default");
                    localStorage.setItem("LocalData", JSON.stringify(data));
                }
            })
            .catch((error) => {
                setInfoState(error);
            });
    };

    return (
        <div className="container loginPageBoxes">
            <form className="width100perc" onSubmit={handleSubmit}>
                <h3>Login with credentials here:</h3>
                <Input
                    type="text"
                    className="input"
                    name="name"
                    value={input.name}
                    placeholder="Enter your username"
                    onChange={changeData}
                />

                <Input
                    type={showPassword ? "text" : "password"}
                    className="input"
                    name="password"
                    value={input.password}
                    placeholder="Enter your password"
                    onChange={changeData}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton onClick={handleClickShowPassword}>
                                {showPassword ? (
                                    <Visibility />
                                ) : (
                                    <VisibilityOff />
                                )}
                            </IconButton>
                        </InputAdornment>
                    }
                />
                <button className="submit" type="submit">
                    Login
                </button>
            </form>
        </div>
    );
}

export default LoginForm;
