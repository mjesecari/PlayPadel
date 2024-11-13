import NavBar from '@/components/navbar';


export default function Layout({userInfo}) {

  if (userInfo){
    console.log("dkjfnskjfn")
  }
  return (
    <div >
    <NavBar userInfo={userInfo}/>
    </div>
  )
}
