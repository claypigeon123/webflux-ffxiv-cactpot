import { MantineProvider } from "@mantine/core";
import { ModalsProvider } from "@mantine/modals";
import { Notifications } from "@mantine/notifications";
import { FC, ReactNode } from "react";


export interface AppWrapperProps {
    children?: ReactNode
}

export const AppWrapper: FC<AppWrapperProps> = ({ children }) => {
    return (
        <>
            <MantineProvider
                theme={{
                    colorScheme: 'dark',
                    primaryColor: 'violet',
                    fontFamily: 'Poppins, sans-serif',
                    fontFamilyMonospace: 'consolas',
                    headings: {
                        fontFamily: 'Poppins, sans-serif'
                    },
                    cursorType: 'pointer'
                }}
                withGlobalStyles
                withNormalizeCSS
            >
                <ModalsProvider
                    modalProps={{
                        size: 'xl',
                        shadow: 'xl',
                        overlayProps: {
                            blur: 4,
                            opacity: 0.6,
                        },
                        padding: 0,
                        withCloseButton: false,
                        closeButtonProps: { size: 'md' },
                        transitionProps: {
                            duration: 250,
                            exitDuration: 250
                        }
                    }}
                >
                    <Notifications />
                    {children}
                </ModalsProvider>
            </MantineProvider>
        </>
    )
}
