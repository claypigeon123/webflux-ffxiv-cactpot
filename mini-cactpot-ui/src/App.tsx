import { useMantineTheme } from "@mantine/core";
import { useMediaQuery } from "@mantine/hooks";
import { FC } from "react";
import { Layout } from "./components/layout/Layout";
import { MiniCactpotGamePage } from "./components/pages/MiniCactpotGamePage";

export const App: FC = () => {
    const { breakpoints } = useMantineTheme();
    const matches = useMediaQuery(`(min-width: ${breakpoints.md})`);

    return (
        <Layout extendedDisplay={matches}>
            <MiniCactpotGamePage extendedDisplay={matches} />
        </Layout>
    )
}