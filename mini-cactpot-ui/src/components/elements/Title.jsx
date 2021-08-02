

export const Title = ({ title, icon, mb }) => {
    return (
        <div className={mb === undefined ? "mb-4" : mb}>
            <div className="border-light pb-1 d-flex" style={style}>
                <div className="mb-0 h4 mr-1">
                    {icon}
                </div>
                <div className="mb-0 h4 mr-auto">
                    {title}
                </div>
            </div>
        </div>
    )
};

const style = {
    borderBottom: '1px solid'
};