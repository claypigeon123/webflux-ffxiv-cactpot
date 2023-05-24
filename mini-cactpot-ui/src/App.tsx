import { useMantineTheme } from "@mantine/core";
import { useMediaQuery } from "@mantine/hooks";
import { FC } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "./components/layout/Layout";
import { MiniCactpotGamePage } from "./components/pages/MiniCactpotGamePage";

export const App: FC = () => {
    const { breakpoints } = useMantineTheme();
    const matches = useMediaQuery(`(min-width: ${breakpoints.md})`);

    return (
        <BrowserRouter>
            <Layout extendedDisplay={matches}>
                <Routes>
                    <Route path='/' element={<MiniCactpotGamePage extendedDisplay={matches} />} />
                </Routes>
            </Layout>
        </BrowserRouter>
    )
}