"use client";


import { Inter } from "next/font/google";
import "./globals.css";
import dynamic from "next/dynamic";
import Header from "./components/common/header";
import { parseCookies } from "nookies";
import DashHeader from "./components/common/dash-header";


const ReduxProvider = dynamic(() => import("@/redux/redux-provider"), {
  ssr: false
});

const inter = Inter({ subsets: ["latin"] });


export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {


// Header 뮤터블 처리, 조건절 추가
  return (
    <html lang="en">
      <body className={inter.className}>
      {parseCookies().message === 'SUCCESS' && <Header/>}
        {/* {parseCookies().message === 'ADMIN' && <DashHeader/>} */}
      {/* <Header/> */}
        <div className="mt-100">
        <ReduxProvider> {children}</ReduxProvider>
        </div>
      </body>
    </html>
  );
}