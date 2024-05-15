import * as React from "react"
 
const  MemberCard = ({member, index}) => {

    console.log(member)
    
    return(<div className="card">
         <div className="absolute bg-white rounded-full border-8 border-green-500"></div>
        <img className="w-32 h-32  mx-auto" src={member.photo} />
        <div className="font-monoplexKRNerd-Bold"> {index+1}등 {member.name}</div>
        <span className="font-monoplexKRNerd-Bold">점수: {member.point}</span>
    </div>)
}


export default MemberCard