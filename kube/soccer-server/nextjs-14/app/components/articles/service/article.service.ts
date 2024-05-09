import { createAsyncThunk } from "@reduxjs/toolkit";
import { findAllArticlesAPI, findArticleByIdAPI, saveArticleAPI } from "./article.api";
import IArticle from "../model/article";


export const findAllArticles: any = createAsyncThunk(
    'articles/findAllArticles', //action
    async (page: number)=>{ //
        console.log('findAllArticles page : '+ page)

        const data : any = await findAllArticlesAPI(1);

        const {message, result}: any = data
    
        // console.log('----- API 를 사용한 경우 -----')
        // console.log('message : '+ message)
        // console.log(JSON.stringify(result))

        return data
    }
)

export const findArticleById: any = createAsyncThunk(
    'articles/findArticleById',
    async (id: number) => {
        return await findArticleByIdAPI(id)
    }
)

export const saveArticle: any = createAsyncThunk(
    'articles/save',
    async (article : IArticle) => await saveArticleAPI(article)
)