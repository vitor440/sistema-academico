import React, { useState } from 'react'
import { DataGrid } from '@mui/x-data-grid'
import Box from '@mui/material/Box'


const CustomTable = ({columns, rows, paginationModel, setPaginationModel, loading, total}) => {
   
  

  return (
    
        <Box >
            <DataGrid  
            rows={rows} 
            columns={columns} // 1360px 
            autoHeight={true} 
            disableRowSelectionOnClick
            loading={loading}
            pagination
            paginationMode='server'
            paginationModel={paginationModel}
            rowCount={total}
            pageSizeOptions={[1, 2, 10, 15, 20]}
            onPaginationModelChange={setPaginationModel}
            density='compact'
            sx={{'& .MuiDataGrid-cell:focus-within': {
                      outline:"none"
                  }}}
            />
        </Box>
    
  )
}

export default CustomTable