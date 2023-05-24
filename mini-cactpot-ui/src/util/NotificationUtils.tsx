import { showNotification } from "@mantine/notifications";
import { FaCheck, FaExclamation } from "react-icons/fa";


export const displaySuccessNotification = (title: string, subtitle: string) => {
    showNotification({
        title: title,
        message: subtitle,
        icon: <FaCheck size='20' />,
        color: 'green'
    });
}

export const displayGenericErrorNotification = () => {
    showNotification({
        title: 'Error',
        message: 'An unexpected error has occurred',
        icon: <FaExclamation size='20' />,
        color: 'red'
    });
}