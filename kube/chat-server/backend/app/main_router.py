from app.api.titanic.web.titanic_router import router as titanic_router

from fastapi import APIRouter


router = APIRouter()

router.include_router(titanic_router, prefix="/chat")