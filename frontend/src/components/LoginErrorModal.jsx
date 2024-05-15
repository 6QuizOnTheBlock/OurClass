import React from 'react';

const LoginErrorModal = ({ isOpen, message, onClose }) => {
    if (!isOpen) return null;

    return (
      <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
        <div className="bg-white p-6 rounded-lg shadow-lg max-w-sm">
          <h2 className="text-lg font-bold mb-2">죄송합니다.</h2>
          <p className="mb-4">{message}</p>
          <button
            onClick={onClose}
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            닫기
          </button>
        </div>
      </div>
    );
};

export default LoginErrorModal;