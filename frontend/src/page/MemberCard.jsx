import * as React from "react"
 
const  MemberCard = ({member}) => {
    
    return(<div className="card">
         <div className="absolute bg-white rounded-full border-8 border-green-500"></div>
        <img className="w-32 h-32  mx-auto" src={member.profileImg} />
        <span className="font-monoplexKRNerd-Bold">대기 중인 친구 : <br/> {member.name}</span>
    </div>)
}


export default MemberCard