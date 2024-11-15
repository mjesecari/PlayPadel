import NavBar from '@/components/navbar';


export default function Layout({userInfo}) {

  return (
    <div >
    <NavBar userInfo={userInfo}/>
    </div>
  )
}
