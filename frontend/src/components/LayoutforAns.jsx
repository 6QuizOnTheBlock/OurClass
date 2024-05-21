const LayoutforAns = ({children}) => {
    return(
    <>
        <div className="bg-[#FFF6F0] min-h-screen flex flex-col items-center justify-center px-3 py-5">
            <div className="bg-[#FFC9D1] p-10 rounded-lg shadow-lg text-center w-full max-w-xs h-[600px] flex flex-col justify-between ">
                <main>
                    {children}
                </main>
            </div>
        </div>
    </>
    )
}


export default LayoutforAns;