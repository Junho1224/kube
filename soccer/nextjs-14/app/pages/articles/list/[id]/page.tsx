"use client";

import { IArticle } from "@/app/components/articles/model/article";
import Columns from "@/app/components/articles/module/columns";
import { findMyList } from "@/app/components/articles/service/article.service";
import { getAllArticles } from "@/app/components/articles/service/article.slice";
import { DataGrid } from "@mui/x-data-grid";
import router from "next/router";
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";


export default function ArticleListPage(props: any) {
    const dispatch = useDispatch()
    const allArticles: IArticle[] = useSelector(getAllArticles)
    

    useEffect(() => {  //즉시 실행 함수
        dispatch(findMyList(props.params.id))
      
    }, []);


    return (
        <>
        <div style={{ display: 'flex', alignItems: 'center' }}>
                <h2>{} 목록</h2>
                <span style={{ marginLeft: 'auto' }}>Count:{}</span>
            </div>

            <div style={{ height: "100%", width: "100%" }}>
                {allArticles && <DataGrid
                    rows={allArticles}
                    columns={Columns()}
                    pageSizeOptions={[5, 10, 20]}
                    checkboxSelection
                />}
            </div>
            ;
        </>

    );
}


//🔥🔥🔥🔥🔥🔥🔥
