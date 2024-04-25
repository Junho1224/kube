import { Link, Typography } from "@mui/material";
import { GridColDef } from "@mui/x-data-grid";
import { ArticleColumn } from "../model/article-column";
import { PG } from "@/app/components/common/enums/PG";
import { Button } from "@mui/material";


interface CellType{
    row : ArticleColumn
}

export default function Columns(): GridColDef[]{


    const onDeleted = () => {
        if (window.confirm("정말 삭제하시겠습니까?")) {
            // dispatch(deleteUserById(props.params.id));
            // router.push(`${PG.USER}/list`);
        }
    };
    const onMod = () => {
        // if (window.confirm("정말 삭제하시겠습니까?")) {
            // dispatch(deleteUserById(props.params.id));
            // router.push(`${PG.USER}/list`);
        // }
    };



    return [
        {
            flex: 0.04,
            minWidth:30,
            sortable: false,
            field: 'id',
            headerName: "No.",
            headerAlign: 'center',
            renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>  {row.id}</Typography>
            

        },
        {
            flex: 0.04,
            minWidth:30,
            sortable: false,
            field: 'title',
            headerName: "제목",
            headerAlign: 'center',
            renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>
                <Link href={`${PG.ARTICLE}/detail/${row.id} `} className="underline" >{row.title}
                </Link>
                </Typography>
            

        },
        {
            flex: 0.04,
            minWidth:30,
            sortable: false,
            field: 'content',
            headerName: "내용",
            headerAlign: 'center',
            renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>  {row.content}</Typography>
            

        },
        {
            flex: 0.04,
            minWidth: 30,
            sortable: false,
            field: 'regDate',
            headerName: '등록일',
            headerAlign: 'center',
            renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>  {row.regDate}</Typography>
        },
        // {
        //     flex: 0.04,
        //     minWidth: 30,
        //     sortable: false,
        //     field: 'modDate',
        //     headerName: '수정일',
        // headerAlign: 'center',
        //     renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>  {row.modDate}</Typography>
        // },
        {
            flex: 0.04,
            minWidth: 30,
            sortable: false,
            field: 'mod',
            headerName: '작업',
            headerAlign: 'center',
            renderCell: ({row}:CellType) =>  <Typography textAlign="center" sx={{fontSize:"1.5rem"}}>  
            {/* <Button onClick={onMod} style={{ background: 'blue', color: 'white' }}>수정하기</Button> */}
            <Button onClick={onDeleted} style={{ background: 'red', color: 'white' }}>삭제하기</Button></Typography>
        },
    ]
        
    
}
