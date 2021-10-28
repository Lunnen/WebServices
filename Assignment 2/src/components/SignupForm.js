import React from "react";

// MUI
import IconButton from "@material-ui/core/IconButton";
import Visibility from "@material-ui/icons/Visibility";
import InputAdornment from "@material-ui/core/InputAdornment";
import VisibilityOff from "@material-ui/icons/VisibilityOff";
import Input from "@material-ui/core/Input";

function SignupForm({ serverURL, setInfoState }) {
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
            method: "put",
            body: JSON.stringify({
                username: input.name,
                password: input.password,
            }),
            headers: {
                "Content-Type": "application/json",
            },
        };

        /* FETCH with text as response */
        await fetch(serverURL + "/user/register", requestOptions)
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

    return (
        <div className="container">
            <form onSubmit={handleSubmit}>
                <h3>Create a new account here:</h3>
                <Input
                    type="text"
                    className="input"
                    name="name"
                    value={input.name}
                    placeholder="Username"
                    onChange={changeData}
                />
                <Input
                    type={showPassword ? "text" : "password"}
                    className="input"
                    name="password"
                    value={input.password}
                    placeholder="Password"
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
                    Create Account
                </button>
            </form>
        </div>
    );
}

export default SignupForm;
