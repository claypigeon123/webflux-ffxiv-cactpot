import { Container, useMantineTheme } from "@mantine/core";
import { useMediaQuery } from "@mantine/hooks";
import { FC } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "./components/layout/Layout";

export const App: FC = () => {
    const { breakpoints } = useMantineTheme();
    const matches = useMediaQuery(`(min-width: ${breakpoints.md})`);

    return (
        <BrowserRouter>
            <Layout>
                <Routes>
                    <Route path='/' element={<>Hello there</>} />
                </Routes>
            </Layout>
        </BrowserRouter>
    )
}