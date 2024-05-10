from fastapi import APIRouter
from pydantic import BaseModel

router = APIRouter()

class Request(BaseModel):
    question : str
class Response(BaseModel):
    answer: str

@router.post("/api/chat/titanic")
async def titanic(req:Request):
    print("titanic 진입"+req.question)
    
    return Response(answer="타이타닉 생존자 수는 100명 입니다.")
