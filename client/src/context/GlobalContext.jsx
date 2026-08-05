import React, { Children, createContext, useState } from 'react'

export const GlobalContext = createContext()

export const GlobalContextProvider = ({children}) => {
    const [openDrawer, setOpenDrawer] = useState(true)

    const [ano, setAno] = useState(new Date().getFullYear())
    const [periodo, setPeriodo] = useState(new Date().getMonth() < 6 ? 1 : 2)
    return (
        <GlobalContext.Provider value={{openDrawer, setOpenDrawer, ano, setAno, periodo, setPeriodo}}>
            {children}
        </GlobalContext.Provider>
    )
}