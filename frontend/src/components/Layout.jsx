const Layout = ({children}) => {
    return(
    <>
        <div className="bg-[#FFF6F0] min-h-screen flex flex-col items-center justify-center px-3 py-5">
            <div className="bg-[#B7DED2] p-10 rounded-lg shadow-lg text-center w-full max-w-4xl ">
                <main>
                    {children}
                </main>
            </div>
        </div>
    </>
    )
}


export default Layout;